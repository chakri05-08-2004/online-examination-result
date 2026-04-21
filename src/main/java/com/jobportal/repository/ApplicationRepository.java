package com.jobportal.repository;

import com.jobportal.entity.Application;
import com.jobportal.entity.ApplicationStatus;
import com.jobportal.entity.Job;
import com.jobportal.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
    
    List<Application> findByStudentOrderByAppliedAtDesc(User student);
    
    List<Application> findByJobOrderByAppliedAtDesc(Job job);
    
    List<Application> findByStatusOrderByAppliedAtDesc(ApplicationStatus status);
    
    long countByJob(Job job);
    
    long countByJobAndStatus(Job job, ApplicationStatus status);
    
    boolean existsByStudentAndJob(User student, Job job);
}
