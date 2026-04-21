package com.jobportal.service;

import com.jobportal.entity.Job;
import com.jobportal.entity.User;
import com.jobportal.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class JobService {

    @Autowired
    private JobRepository jobRepository;

    public Job saveJob(Job job) {
        return jobRepository.save(job);
    }

    public List<Job> findAllJobs() {
        return jobRepository.findByOrderByPostedAtDesc();
    }

    public List<Job> findWithFilters(String category, String location, String experience) {
        if ((category == null || category.isEmpty()) && 
            (location == null || location.isEmpty()) && 
            (experience == null || experience.isEmpty())) {
            return findAllJobs();
        }
        return jobRepository.findWithFilters(category, location, experience);
    }

    public Job findById(Long id) {
        return jobRepository.findById(id).orElse(null);
    }

    public void deleteJob(Long id) {
        jobRepository.deleteById(id);
    }

    public List<Job> findByEmployer(User employer) {
        return jobRepository.findByEmployerOrderByPostedAtDesc(employer);
    }

    public long countAll() {
        return jobRepository.count();
    }
}
