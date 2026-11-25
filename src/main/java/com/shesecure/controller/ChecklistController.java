package com.shesecure.controller;
import com.shesecure.model.*; import com.shesecure.repo.*; import org.springframework.stereotype.Controller; import org.springframework.web.bind.annotation.*; import java.security.Principal; import java.util.*;
@Controller
public class ChecklistController {
 private final UserRepository users;private final ChecklistRepository checks;
 private static final List<String> KEYS=List.of("two-factor","password","privacy","sessions","sharing");
 public ChecklistController(UserRepository u,ChecklistRepository c){users=u;checks=c;}
 @PostMapping("/checklist") public String update(@RequestParam(required=false) List<String> completed,Principal p){
  AppUser user=users.findByEmail(p.getName()).orElseThrow();Set<String> selected=completed==null?Set.of():new HashSet<>(completed);
  for(String key:KEYS){var item=checks.findByOwnerAndItemKey(user,key).orElseGet(()->{var n=new ChecklistProgress();n.setOwner(user);n.setItemKey(key);return n;});item.setCompleted(selected.contains(key));checks.save(item);}
  return "redirect:/dashboard";
 }
}
