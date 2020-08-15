package com.uyth.gryttr.repository;

import com.uyth.gryttr.model.Boulder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoulderRepository extends JpaRepository<Boulder, Long> {
}
