package com.braingrow.dto;
import com.braingrow.entity.Role;
import java.time.LocalDateTime; import java.util.UUID;
public record UserSummary(UUID id,String email,Integer age,Role role,boolean enabled,LocalDateTime createdAt) {}
