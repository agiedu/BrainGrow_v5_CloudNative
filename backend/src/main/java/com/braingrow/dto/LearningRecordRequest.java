package com.braingrow.dto;
import jakarta.validation.constraints.*;
import java.util.UUID;
public record LearningRecordRequest(@NotNull UUID exerciseId,@NotNull @Min(0) @Max(100) Integer score) {}
