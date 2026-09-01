package com.braingrow.controller;
import com.braingrow.dto.ExerciseResponse; import com.braingrow.repository.ExerciseRepository; import org.springframework.web.bind.annotation.*; import java.util.*;
@RestController @RequestMapping("/api/exercises") public class ExerciseController {
 private final ExerciseRepository repo; public ExerciseController(ExerciseRepository r){repo=r;}
 @GetMapping public List<ExerciseResponse> all(@RequestParam(required=false) String type){var list=type==null?repo.findAll():repo.findByTypeIgnoreCaseOrderByTitleAsc(type);return list.stream().map(ExerciseResponse::student).toList();}
 @GetMapping("/{id}") public ExerciseResponse one(@PathVariable UUID id){return repo.findById(id).map(ExerciseResponse::student).orElseThrow(()->new NoSuchElementException("Exercise not found"));}
}
