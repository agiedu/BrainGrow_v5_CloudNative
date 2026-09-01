package com.braingrow.controller;
import com.braingrow.dto.*; import com.braingrow.service.AuthService; import jakarta.validation.Valid; import org.springframework.http.ResponseEntity; import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/auth") public class AuthController {
 private final AuthService auth; public AuthController(AuthService a){auth=a;}
 @PostMapping("/register") public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest r){auth.register(r);return ResponseEntity.ok(java.util.Map.of("message","Registration successful"));}
 @PostMapping("/login") public AuthResponse login(@Valid @RequestBody LoginRequest r){return auth.login(r);}
 @PostMapping("/send-code") public ResponseEntity<?> code(@Valid @RequestBody SendCodeRequest r){auth.sendResetCode(r.email());return ResponseEntity.ok(java.util.Map.of("message","If the account exists, a verification code was sent"));}
 @PostMapping("/reset-password") public ResponseEntity<?> reset(@Valid @RequestBody ResetPasswordRequest r){auth.reset(r);return ResponseEntity.ok(java.util.Map.of("message","Password reset successful"));}
}
