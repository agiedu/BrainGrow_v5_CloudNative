package com.braingrow.entity;
import jakarta.persistence.*; import java.time.LocalDateTime; import java.util.UUID;
@Entity @Table(name="verification_codes")
public class VerificationCode {
 @Id @GeneratedValue(strategy=GenerationType.UUID) private UUID id;
 @Column(nullable=false,length=255) private String email;
 @Column(nullable=false,length=6) private String code;
 @Column(nullable=false,length=30) private String purpose;
 @Column(name="expires_at",nullable=false) private LocalDateTime expiresAt;
 public UUID getId(){return id;} public String getEmail(){return email;} public void setEmail(String v){email=v;} public String getCode(){return code;} public void setCode(String v){code=v;}
 public String getPurpose(){return purpose;} public void setPurpose(String v){purpose=v;} public LocalDateTime getExpiresAt(){return expiresAt;} public void setExpiresAt(LocalDateTime v){expiresAt=v;}
}
