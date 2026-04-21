package com.jobportal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    @Autowired(required = false)
    private JavaMailSender mailSender;

    public void sendEmail(String to, String subject, String body) {
        if (mailSender == null) {
            System.out.println("MailSender not configured. Email to: " + to + " | Subject: " + subject);
            return;
        }
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("jobportal@example.com");
            message.setTo(to);
            message.setSubject(subject);
            message.setText(body);
            mailSender.send(message);
        } catch (Exception e) {
            System.err.println("Failed to send email: " + e.getMessage());
        }
    }

    public void sendShortlistNotification(String to, String studentName, String jobTitle) {
        String body = String.format("Dear %s,\n\nCongratulations! You have been shortlisted for the position of '%s'. The employer will contact you soon for the next steps.\n\nBest regards,\nJob Portal Team", 
                                    studentName, jobTitle);
        sendEmail(to, "Job Shortlisted: " + jobTitle, body);
    }
}
