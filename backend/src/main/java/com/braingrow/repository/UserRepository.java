package com.braingrow.repository;
import com.braingrow.entity.User; import java.util.*; import org.springframework.data.jpa.repository.JpaRepository;
public interface UserRepository extends JpaRepository<User,UUID>{Optional<User> findByEmailIgnoreCase(String email); boolean existsByEmailIgnoreCase(String email); long countByEnabledTrue();}
