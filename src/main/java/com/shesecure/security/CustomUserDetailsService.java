package com.shesecure.security;
import com.shesecure.repo.UserRepository; import org.springframework.security.core.userdetails.*; import org.springframework.stereotype.Service;
@Service
public class CustomUserDetailsService implements UserDetailsService {
 private final UserRepository users;
 public CustomUserDetailsService(UserRepository users){this.users=users;}
 public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
  var u=users.findByEmail(email).orElseThrow(()->new UsernameNotFoundException("Account not found"));
  return User.withUsername(u.getEmail()).password(u.getPassword()).roles("USER").build();
 }
}
