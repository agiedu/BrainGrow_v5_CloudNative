package com.braingrow.service;
import com.braingrow.entity.*; import com.braingrow.repository.UserRepository; import org.springframework.beans.factory.annotation.Value; import org.springframework.boot.CommandLineRunner; import org.springframework.security.crypto.password.PasswordEncoder; import org.springframework.stereotype.Component;
@Component public class AdminBootstrap implements CommandLineRunner {
 private final UserRepository users; private final PasswordEncoder encoder; private final String email,password;
 public AdminBootstrap(UserRepository u,PasswordEncoder e,@Value("${app.admin-email:}") String email,@Value("${app.admin-password:}") String password){this.users=u;this.encoder=e;this.email=email;this.password=password;}
 @Override public void run(String... args){if(email==null||email.isBlank()||password==null||password.length()<8)return;String em=email.trim().toLowerCase();User u=users.findByEmailIgnoreCase(em).orElseGet(()->{User n=new User();n.setEmail(em);return n;});if(u.getPasswordHash()==null||!u.getRole().equals(Role.ADMIN)){u.setPasswordHash(encoder.encode(password));u.setAge(18);u.setRole(Role.ADMIN);u.setEnabled(true);users.save(u);}}
}
