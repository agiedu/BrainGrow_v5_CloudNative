package com.braingrow.dto;
import jakarta.validation.constraints.*; import java.util.UUID;
public record LearningSubmitRequest(@NotNull UUID exerciseId,@NotBlank @Size(max=500) String answer) {}
