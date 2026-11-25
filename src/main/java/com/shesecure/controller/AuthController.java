package com.shesecure.controller;
import com.shesecure.model.AppUser; import com.shesecure.repo.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder; import org.springframework.stereotype.Controller; import org.springframework.ui.Model; import org.springframework.web.bind.annotation.*;
@Controller
public class AuthController {
 private final UserRepository users; private final PasswordEncoder encoder;
 public AuthController(UserRepository users,PasswordEncoder encoder){this.users=users;this.encoder=encoder;}
 @GetMapping("/") public String home(){return "redirect:/login";}
 @GetMapping("/login") public String login(){return "login";}
 @GetMapping("/register") public String register(){return "register";}
 @PostMapping("/register") public String create(@RequestParam String fullName,@RequestParam String email,@RequestParam String password,Model model){
  if(password.length()<8){model.addAttribute("error","Password must contain at least 8 characters.");return "register";}
  if(users.findByEmail(email.toLowerCase().trim()).isPresent()){model.addAttribute("error","An account with this email already exists.");return "register";}
  AppUser u=new AppUser();u.setFullName(fullName.trim());u.setEmail(email.toLowerCase().trim());u.setPassword(encoder.encode(password));users.save(u);return "redirect:/login?registered";
 }
}
