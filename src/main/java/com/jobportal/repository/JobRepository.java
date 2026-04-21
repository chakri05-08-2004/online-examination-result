package com.jobportal.repository;

import com.jobportal.entity.Job;
import com.jobportal.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface JobRepository extends JpaRepository<Job, Long> {
    
    List<Job> findByOrderByPostedAtDesc();
    
    List<Job> findByCategoryOrderByPostedAtDesc(String category);
    
    List<Job> findByLocationOrderByPostedAtDesc(String location);
    
    List<Job> findByExperienceOrderByPostedAtDesc(String experience);
    
    List<Job> findByEmployerOrderByPostedAtDesc(User employer);

    @Query("SELECT j FROM Job j WHERE " +
           "(:category IS NULL OR j.category = :category) AND " +
           "(:location IS NULL OR j.location = :location) AND " +
           "(:experience IS NULL OR j.experience = :experience) " +
           "ORDER BY j.postedAt DESC")
    List<Job> findWithFilters(@Param("category") String category, 
                              @Param("location") String location, 
                              @Param("experience") String experience);

    long countByEmployer(User employer);
}
