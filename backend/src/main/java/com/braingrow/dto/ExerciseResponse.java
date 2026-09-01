package com.braingrow.dto;
import com.braingrow.entity.Exercise;
import java.util.UUID;
public record ExerciseResponse(UUID id,String title,String type,String difficulty,String question,String answer) {
  public static ExerciseResponse student(Exercise e){return new ExerciseResponse(e.getId(),e.getTitle(),e.getType(),e.getDifficulty(),e.getQuestion(),null);}
  public static ExerciseResponse admin(Exercise e){return new ExerciseResponse(e.getId(),e.getTitle(),e.getType(),e.getDifficulty(),e.getQuestion(),e.getAnswer());}
}
