package com.braingrow.entity;
import jakarta.persistence.*; import java.time.LocalDateTime; import java.util.UUID;
@Entity @Table(name="users")
public class User {
 @Id @GeneratedValue(strategy=GenerationType.UUID) private UUID id;
 @Column(nullable=false,unique=true,length=255) private String email;
 @Column(name="password_hash",nullable=false) private String passwordHash;
 @Column(nullable=false) private Integer age;
 @Enumerated(EnumType.STRING) @Column(nullable=false,length=20) private Role role=Role.STUDENT;
 @Column(nullable=false) private boolean enabled=true;
 @Column(name="created_at",nullable=false) private LocalDateTime createdAt=LocalDateTime.now();
 public UUID getId(){return id;} public String getEmail(){return email;} public void setEmail(String v){email=v;}
 public String getPasswordHash(){return passwordHash;} public void setPasswordHash(String v){passwordHash=v;}
 public Integer getAge(){return age;} public void setAge(Integer v){age=v;}
 public Role getRole(){return role;} public void setRole(Role v){role=v;}
 public boolean isEnabled(){return enabled;} public void setEnabled(boolean v){enabled=v;}
 public LocalDateTime getCreatedAt(){return createdAt;}
}
