package com.braingrow.repository;
import com.braingrow.entity.LearningRecord; import java.util.*; import org.springframework.data.jpa.repository.JpaRepository;
public interface LearningRecordRepository extends JpaRepository<LearningRecord,UUID>{List<LearningRecord> findByUserIdOrderByCreatedAtDesc(UUID userId); long countBy();}
