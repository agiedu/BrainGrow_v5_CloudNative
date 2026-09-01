package com.braingrow.dto;
import java.util.UUID;
public record LearningSubmitResponse(UUID exerciseId,int score,boolean correct,String correctAnswer) {}
