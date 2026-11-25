package com.shesecure.controller;
import com.shesecure.repo.*; import com.shesecure.model.*; import org.springframework.stereotype.Controller; import org.springframework.ui.Model; import org.springframework.web.bind.annotation.*; import java.security.Principal; import java.util.*;
@Controller
public class DashboardController {
 private final UserRepository users; private final IncidentRepository incidents; private final ChecklistRepository checks;
 public DashboardController(UserRepository u,IncidentRepository i,ChecklistRepository c){users=u;incidents=i;checks=c;}
 private AppUser current(Principal p){return users.findByEmail(p.getName()).orElseThrow();}
 @GetMapping("/dashboard") public String dashboard(Model m,Principal p){
  AppUser u=current(p); var all=incidents.findByOwnerOrderByIncidentDateDesc(u); var progress=checks.findByOwner(u);
  m.addAttribute("user",u);m.addAttribute("incidents",all);m.addAttribute("incidentCount",all.size());
  m.addAttribute("checklist",checkItems());m.addAttribute("progress",progress);
  long done=progress.stream().filter(ChecklistProgress::isCompleted).count();m.addAttribute("score",Math.round(done*100.0/5));
  return "dashboard";
 }
 @GetMapping("/education") public String education(){return "education";}
 private List<String> checkItems(){return List.of("Enable two-factor authentication","Use a unique, strong password","Review social media privacy settings","Check active sessions and logged-in devices","Avoid sharing personal information publicly");}
}
