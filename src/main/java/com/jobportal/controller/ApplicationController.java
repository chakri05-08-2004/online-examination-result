package com.jobportal.controller;

import com.jobportal.entity.Application;
import com.jobportal.entity.ApplicationStatus;
import com.jobportal.entity.Job;
import com.jobportal.entity.User;
import com.jobportal.service.ApplicationService;
import com.jobportal.service.JobService;
import com.jobportal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@Controller
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private JobService jobService;

    @Autowired
    private UserService userService;

    @PostMapping("/apply/{jobId}")
    public String applyForJob(@PathVariable Long jobId,
                             @RequestParam("resume") MultipartFile resume,
                             @AuthenticationPrincipal UserDetails userDetails,
                             Model model) {
        User student = userService.findByEmail(userDetails.getUsername()).get();
        Job job = jobService.findById(jobId);
        
        try {
            applicationService.applyForJob(student, job, resume);
        } catch (IOException | RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("job", job);
            return "job-detail";
        }
        
        return "redirect:/student/dashboard?applied";
    }

    @GetMapping("/applicants/{jobId}")
    public String viewApplicants(@PathVariable Long jobId, Model model) {
        Job job = jobService.findById(jobId);
        model.addAttribute("job", job);
        model.addAttribute("applicants", applicationService.findByJob(job));
        return "applicant-list";
    }

    @PostMapping("/applications/{id}/status")
    public String updateStatus(@PathVariable Long id, 
                              @RequestParam ApplicationStatus status,
                              @RequestParam Long jobId) {
        applicationService.updateStatus(id, status);
        return "redirect:/applicants/" + jobId;
    }
}
