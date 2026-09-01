package com.braingrow.dto;
import jakarta.validation.constraints.*;
public record RegisterRequest(@Email @NotBlank String email,@NotBlank @Size(min=8,max=128) String password,@NotNull @Min(7) @Max(18) Integer age) {}
