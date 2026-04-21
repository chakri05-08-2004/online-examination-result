package com.jobportal.service;

import com.jobportal.entity.Application;
import com.jobportal.entity.ApplicationStatus;
import com.jobportal.entity.Job;
import com.jobportal.entity.User;
import com.jobportal.repository.ApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Service
public class ApplicationService {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private MailService mailService;

    @org.springframework.beans.factory.annotation.Value("${file.upload-dir:uploads/}")
    private String uploadDir;

    public Application applyForJob(User student, Job job, MultipartFile resume) throws IOException {
        if (applicationRepository.existsByStudentAndJob(student, job)) {
            throw new RuntimeException("You have already applied for this job.");
        }

        String fileName = UUID.randomUUID().toString() + "_" + resume.getOriginalFilename();
        Path path = Paths.get(uploadDir);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }

        Path filePath = path.resolve(fileName);
        Files.copy(resume.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        Application application = new Application();
        application.setStudent(student);
        application.setJob(job);
        application.setResumePath(fileName);
        application.setStatus(ApplicationStatus.APPLIED);

        return applicationRepository.save(application);
    }

    public List<Application> findByStudent(User student) {
        return applicationRepository.findByStudentOrderByAppliedAtDesc(student);
    }

    public List<Application> findByJob(Job job) {
        return applicationRepository.findByJobOrderByAppliedAtDesc(job);
    }

    public Application updateStatus(Long applicationId, ApplicationStatus status) {
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Application not found"));
        
        application.setStatus(status);
        Application updated = applicationRepository.save(application);

        if (status == ApplicationStatus.SHORTLISTED) {
            mailService.sendShortlistNotification(
                    updated.getStudent().getEmail(), 
                    updated.getStudent().getName(), 
                    updated.getJob().getTitle()
            );
        }

        return updated;
    }

    public long countAll() {
        return applicationRepository.count();
    }

    public long countByStatus(ApplicationStatus status) {
        // Simple implementation, could be optimized in repo
        return applicationRepository.findAll().stream()
                .filter(a -> a.getStatus() == status)
                .count();
    }
}
