package com.braingrow.dto;
import jakarta.validation.constraints.*;
public record ResetPasswordRequest(@Email @NotBlank String email,@NotBlank @Pattern(regexp="\\d{6}") String code,@NotBlank @Size(min=8,max=128) String newPassword) {}
