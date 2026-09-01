package com.braingrow.entity;
import jakarta.persistence.*; import java.time.LocalDateTime; import java.util.UUID;
@Entity @Table(name="learning_records")
public class LearningRecord {
 @Id @GeneratedValue(strategy=GenerationType.UUID) private UUID id;
 @Column(name="user_id",nullable=false) private UUID userId;
 @Column(name="exercise_id",nullable=false) private UUID exerciseId;
 @Column(nullable=false) private int score;
 @Column(name="created_at",nullable=false) private LocalDateTime createdAt=LocalDateTime.now();
 public UUID getId(){return id;} public UUID getUserId(){return userId;} public void setUserId(UUID v){userId=v;}
 public UUID getExerciseId(){return exerciseId;} public void setExerciseId(UUID v){exerciseId=v;} public int getScore(){return score;} public void setScore(int v){score=v;}
 public LocalDateTime getCreatedAt(){return createdAt;}
}
