package com.braingrow.dto;
import jakarta.validation.constraints.*;
public record SendCodeRequest(@Email @NotBlank String email) {}
