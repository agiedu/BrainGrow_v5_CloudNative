package com.braingrow.service;
import org.springframework.beans.factory.annotation.Value; import org.springframework.mail.SimpleMailMessage; import org.springframework.mail.javamail.JavaMailSender; import org.springframework.stereotype.Service;
@Service public class EmailService {
 private final JavaMailSender sender; private final String username;
 public EmailService(JavaMailSender sender,@Value("${spring.mail.username:}") String username){this.sender=sender;this.username=username;}
 public void sendCode(String email,String code){if(username==null||username.isBlank()){System.out.println("[BrainGrow DEV] verification code for "+email+": "+code);return;}
  SimpleMailMessage m=new SimpleMailMessage();m.setTo(email);m.setFrom(username);m.setSubject("BrainGrow password reset code");m.setText("Your BrainGrow verification code is "+code+". It expires in 10 minutes.");sender.send(m);}
}
