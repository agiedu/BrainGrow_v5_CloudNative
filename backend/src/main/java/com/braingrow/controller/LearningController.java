package com.braingrow.controller;
import com.braingrow.dto.*; import com.braingrow.entity.LearningRecord; import com.braingrow.repository.*; import jakarta.validation.Valid; import org.springframework.security.core.Authentication; import org.springframework.web.bind.annotation.*; import java.util.*;
@RestController @RequestMapping("/api/learning") public class LearningController {
 private final LearningRecordRepository records; private final UserRepository users; private final ExerciseRepository exercises;
 public LearningController(LearningRecordRepository r,UserRepository u,ExerciseRepository e){records=r;users=u;exercises=e;}
 @GetMapping("/me") public List<LearningRecord> me(Authentication a){var u=users.findByEmailIgnoreCase(a.getName()).orElseThrow();return records.findByUserIdOrderByCreatedAtDesc(u.getId());}
 @PostMapping("/submit") public LearningSubmitResponse submit(Authentication a,@Valid @RequestBody LearningSubmitRequest req){
  var u=users.findByEmailIgnoreCase(a.getName()).orElseThrow();
  var e=exercises.findById(req.exerciseId()).orElseThrow(()->new NoSuchElementException("Exercise not found"));
  boolean correct=e.getAnswer().trim().equalsIgnoreCase(req.answer().trim());
  int score=correct?100:0; LearningRecord r=new LearningRecord();r.setUserId(u.getId());r.setExerciseId(e.getId());r.setScore(score);records.save(r);
  return new LearningSubmitResponse(e.getId(),score,correct,e.getAnswer());
 }
}
