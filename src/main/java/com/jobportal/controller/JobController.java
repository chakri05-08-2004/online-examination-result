package com.jobportal.controller;

import com.jobportal.entity.Job;
import com.jobportal.entity.User;
import com.jobportal.service.JobService;
import com.jobportal.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/jobs")
public class JobController {

    @Autowired
    private JobService jobService;

    @Autowired
    private UserService userService;

    @GetMapping
    public String listJobs(@RequestParam(required = false) String category,
                           @RequestParam(required = false) String location,
                           @RequestParam(required = false) String experience,
                           Model model) {
        model.addAttribute("jobs", jobService.findWithFilters(category, location, experience));
        return "job-list";
    }

    @GetMapping("/{id}")
    public String viewJob(@PathVariable Long id, Model model) {
        Job job = jobService.findById(id);
        if (job == null) return "error/error";
        model.addAttribute("job", job);
        return "job-detail";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("job", new Job());
        return "job-form";
    }

    @PostMapping("/new")
    public String createJob(@Valid @ModelAttribute("job") Job job,
                           BindingResult result,
                           @AuthenticationPrincipal UserDetails userDetails) {
        if (result.hasErrors()) {
            return "job-form";
        }
        User employer = userService.findByEmail(userDetails.getUsername()).get();
        job.setEmployer(employer);
        jobService.saveJob(job);
        return "redirect:/employer/dashboard";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("job", jobService.findById(id));
        return "job-form";
    }

    @PostMapping("/edit/{id}")
    public String updateJob(@PathVariable Long id, @Valid @ModelAttribute("job") Job job,
                           BindingResult result) {
        if (result.hasErrors()) {
            return "job-form";
        }
        Job existing = jobService.findById(id);
        job.setEmployer(existing.getEmployer());
        job.setPostedAt(existing.getPostedAt());
        jobService.saveJob(job);
        return "redirect:/employer/dashboard";
    }

    @GetMapping("/delete/{id}")
    public String deleteJob(@PathVariable Long id) {
        jobService.deleteJob(id);
        return "redirect:/employer/dashboard";
    }
}
