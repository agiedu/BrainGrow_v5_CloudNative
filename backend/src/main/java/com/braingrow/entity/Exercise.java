package com.braingrow.entity;
import jakarta.persistence.*; import java.util.UUID;
@Entity @Table(name="exercises")
public class Exercise {
 @Id @GeneratedValue(strategy=GenerationType.UUID) private UUID id;
 @Column(nullable=false,length=160) private String title;
 @Column(nullable=false,length=50) private String type;
 @Column(nullable=false,length=30) private String difficulty;
 @Column(nullable=false,columnDefinition="text") private String question;
 @Column(nullable=false,columnDefinition="text") private String answer;
 public UUID getId(){return id;} public String getTitle(){return title;} public void setTitle(String v){title=v;}
 public String getType(){return type;} public void setType(String v){type=v;} public String getDifficulty(){return difficulty;} public void setDifficulty(String v){difficulty=v;}
 public String getQuestion(){return question;} public void setQuestion(String v){question=v;} public String getAnswer(){return answer;} public void setAnswer(String v){answer=v;}
}
