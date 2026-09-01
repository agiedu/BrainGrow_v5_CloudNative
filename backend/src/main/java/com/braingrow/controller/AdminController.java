package com.braingrow.controller;
import com.braingrow.dto.*; import com.braingrow.entity.*; import com.braingrow.repository.*; import jakarta.validation.Valid; import org.springframework.web.bind.annotation.*; import java.util.*;
@RestController @RequestMapping("/api/admin") public class AdminController {
 private final UserRepository users; private final ExerciseRepository exercises; private final LearningRecordRepository records;
 public AdminController(UserRepository u,ExerciseRepository e,LearningRecordRepository r){users=u;exercises=e;records=r;}
 @GetMapping("/users") public List<UserSummary> users(){return users.findAll().stream().map(u->new UserSummary(u.getId(),u.getEmail(),u.getAge(),u.getRole(),u.isEnabled(),u.getCreatedAt())).toList();}
 @PutMapping("/users/{id}/status") public UserSummary status(@PathVariable UUID id,@RequestParam boolean enabled){User u=users.findById(id).orElseThrow(()->new NoSuchElementException("User not found"));u.setEnabled(enabled);u=users.save(u);return new UserSummary(u.getId(),u.getEmail(),u.getAge(),u.getRole(),u.isEnabled(),u.getCreatedAt());}
 @PutMapping("/users/{id}/role") public UserSummary role(@PathVariable UUID id,@RequestParam Role role){User u=users.findById(id).orElseThrow(()->new NoSuchElementException("User not found"));u.setRole(role);u=users.save(u);return new UserSummary(u.getId(),u.getEmail(),u.getAge(),u.getRole(),u.isEnabled(),u.getCreatedAt());}
 @GetMapping("/exercises") public List<ExerciseResponse> exercises(){return exercises.findAll().stream().map(ExerciseResponse::admin).toList();}
 @PostMapping("/exercises") public ExerciseResponse create(@Valid @RequestBody ExerciseRequest r){Exercise e=new Exercise();apply(e,r);return ExerciseResponse.admin(exercises.save(e));}
 @PutMapping("/exercises/{id}") public ExerciseResponse update(@PathVariable UUID id,@Valid @RequestBody ExerciseRequest r){Exercise e=exercises.findById(id).orElseThrow(()->new NoSuchElementException("Exercise not found"));apply(e,r);return ExerciseResponse.admin(exercises.save(e));}
 @DeleteMapping("/exercises/{id}") public void delete(@PathVariable UUID id){if(!exercises.existsById(id))throw new NoSuchElementException("Exercise not found");exercises.deleteById(id);}
 @GetMapping("/stats") public StatsResponse stats(){return new StatsResponse(users.count(),users.countByEnabledTrue(),exercises.count(),records.count());}
 private void apply(Exercise e,ExerciseRequest r){e.setTitle(r.title().trim());e.setType(r.type().trim().toLowerCase());e.setDifficulty(r.difficulty().trim().toLowerCase());e.setQuestion(r.question().trim());e.setAnswer(r.answer().trim());}
}
