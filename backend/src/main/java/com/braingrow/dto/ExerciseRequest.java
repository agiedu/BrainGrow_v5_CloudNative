package com.braingrow.dto;
import jakarta.validation.constraints.*;
public record ExerciseRequest(@NotBlank @Size(max=160) String title,@NotBlank @Size(max=50) String type,@NotBlank @Size(max=30) String difficulty,@NotBlank String question,@NotBlank String answer) {}
