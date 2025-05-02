package com.example.demotekton.repository;

import com.example.demotekton.entity.CallLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CallLogRepository extends JpaRepository<CallLog, Long> {

}
