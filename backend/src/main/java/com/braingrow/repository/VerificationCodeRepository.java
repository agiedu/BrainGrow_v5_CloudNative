package com.braingrow.repository;
import com.braingrow.entity.VerificationCode; import java.util.*; import org.springframework.data.jpa.repository.JpaRepository;
public interface VerificationCodeRepository extends JpaRepository<VerificationCode,UUID>{
 Optional<VerificationCode> findTopByEmailIgnoreCaseAndPurposeOrderByExpiresAtDesc(String email,String purpose);
 void deleteByEmailIgnoreCaseAndPurpose(String email,String purpose);
}
