package com.braingrow.repository;
import com.braingrow.entity.Exercise; import java.util.*; import org.springframework.data.jpa.repository.JpaRepository;
public interface ExerciseRepository extends JpaRepository<Exercise,UUID>{List<Exercise> findByTypeIgnoreCaseOrderByTitleAsc(String type); long countBy();}
