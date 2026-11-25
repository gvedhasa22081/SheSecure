package com.shesecure.controller;
import com.shesecure.model.*; import com.shesecure.repo.*; import com.shesecure.service.StorageService;
import org.springframework.stereotype.Controller; import org.springframework.ui.Model; import org.springframework.web.bind.annotation.*; import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.*; import java.security.Principal; import java.time.LocalDate; import java.util.*;
@Controller
public class IncidentController {
 private final UserRepository users; private final IncidentRepository incidents; private final EvidenceRepository evidence; private final StorageService storage;
 public IncidentController(UserRepository u,IncidentRepository i,EvidenceRepository e,StorageService s){users=u;incidents=i;evidence=e;storage=s;}
 private AppUser current(Principal p){return users.findByEmail(p.getName()).orElseThrow();}
 @GetMapping("/incidents/new") public String form(){return "incident-form";}
 @PostMapping("/incidents") public String save(@RequestParam String title,@RequestParam String category,@RequestParam(required=false) String platform,@RequestParam String incidentDate,@RequestParam String description,@RequestParam(required=false) List<MultipartFile> files,Principal p)throws Exception{
  Incident x=new Incident();x.setTitle(title);x.setCategory(category);x.setPlatform(platform);x.setIncidentDate(LocalDate.parse(incidentDate));x.setDescription(description);x.setOwner(current(p));incidents.save(x);
  if(files!=null)for(MultipartFile f:files){if(!f.isEmpty()){if(f.getSize()>10*1024*1024)continue;String key=storage.save(f.getBytes());Evidence ev=new Evidence();ev.setOriginalName(f.getOriginalFilename()==null?"evidence":f.getOriginalFilename().replaceAll("[\\r\\n]",""));ev.setContentType(f.getContentType());ev.setStorageKey(key);ev.setIncident(x);evidence.save(ev);}}
  return "redirect:/incidents/"+x.getId();
 }
 @GetMapping("/incidents/{id}") public String detail(@PathVariable Long id,Principal p,Model m){Incident x=incidents.findByIdAndOwner(id,current(p)).orElseThrow();m.addAttribute("incident",x);m.addAttribute("evidence",evidence.findByIncidentOrderByUploadedAtDesc(x));return "incident-detail";}
 @GetMapping("/evidence/{id}") public ResponseEntity<byte[]> download(@PathVariable Long id,Principal p)throws Exception{
  AppUser u=current(p); Evidence ev=evidence.findById(id).orElseThrow();if(!ev.getIncident().getOwner().getId().equals(u.getId()))return ResponseEntity.notFound().build();
  byte[] bytes=storage.read(ev.getStorageKey());String type=ev.getContentType()==null?"application/octet-stream":ev.getContentType();
  return ResponseEntity.ok().contentType(MediaType.parseMediaType(type)).header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\""+ev.getOriginalName().replace("\"","")+"\"").body(bytes);
 }
 @PostMapping("/incidents/{id}/delete") public String delete(@PathVariable Long id,Principal p)throws Exception{
  Incident x=incidents.findByIdAndOwner(id,current(p)).orElseThrow();for(Evidence ev:evidence.findByIncidentOrderByUploadedAtDesc(x)){storage.delete(ev.getStorageKey());evidence.delete(ev);}incidents.delete(x);return "redirect:/dashboard";
 }
}
