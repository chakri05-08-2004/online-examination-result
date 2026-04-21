package com.jobportal.controller;

import com.jobportal.entity.Job;
import com.jobportal.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/jobs")
public class JobRestController {

    @Autowired
    private JobService jobService;

    @GetMapping
    public List<Job> getAllJobs(@RequestParam(required = false) String category,
                                @RequestParam(required = false) String location,
                                @RequestParam(required = false) String experience) {
        return jobService.findWithFilters(category, location, experience);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Job> getJobById(@PathVariable Long id) {
        Job job = jobService.findById(id);
        return job != null ? ResponseEntity.ok(job) : ResponseEntity.notFound().build();
    }
}
