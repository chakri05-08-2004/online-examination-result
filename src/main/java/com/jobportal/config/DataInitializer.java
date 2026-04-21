package com.jobportal.config;

import com.jobportal.entity.Job;
import com.jobportal.entity.Role;
import com.jobportal.entity.User;
import com.jobportal.repository.JobRepository;
import com.jobportal.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository, JobRepository jobRepository, PasswordEncoder encoder) {
        return args -> {
            if (userRepository.count() == 0) {
                User admin = new User();
                admin.setName("Admin User");
                admin.setEmail("admin@jobportal.com");
                admin.setPassword(encoder.encode("admin123"));
                admin.setRole(Role.ADMIN);
                userRepository.save(admin);

                User employer = new User();
                employer.setName("Tech Corp");
                employer.setEmail("employer@techcorp.com");
                employer.setPassword(encoder.encode("employer123"));
                employer.setRole(Role.EMPLOYER);
                userRepository.save(employer);

                User student = new User();
                student.setName("Jane Smith");
                student.setEmail("student@gmail.com");
                student.setPassword(encoder.encode("student123"));
                student.setRole(Role.STUDENT);
                userRepository.save(student);

                Job job1 = new Job();
                job1.setTitle("Senior Java Developer");
                job1.setDescription("Join our core engineering team to build scalable microservices using Spring Boot and Kafka.");
                job1.setCategory("Engineering");
                job1.setLocation("Remote");
                job1.setSalary(120000.0);
                job1.setExperience("Senior Level");
                job1.setSkillsRequired("Java, Spring Boot, Kafka, Kubernetes");
                job1.setEmployer(employer);
                jobRepository.save(job1);

                Job job2 = new Job();
                job2.setTitle("Product Designer");
                job2.setDescription("We are looking for a creative UI/UX designer to lead our platform redesign. Figma expertise required.");
                job2.setCategory("Design");
                job2.setLocation("San Francisco, CA");
                job2.setSalary(110000.0);
                job2.setExperience("Mid Level");
                job2.setSkillsRequired("Figma, Adobe XD, HTML/CSS");
                job2.setEmployer(employer);
                jobRepository.save(job2);

                Job job3 = new Job();
                job3.setTitle("Frontend React Developer");
                job3.setDescription("Seeking a frontend wizard to build responsive web applications using React and TypeScript.");
                job3.setCategory("Engineering");
                job3.setLocation("New York, NY");
                job3.setSalary(105000.0);
                job3.setExperience("Mid Level");
                job3.setSkillsRequired("React, TypeScript, Redux, Tailwind CSS");
                job3.setEmployer(employer);
                jobRepository.save(job3);

                Job job4 = new Job();
                job4.setTitle("Data Scientist");
                job4.setDescription("Analyze complex datasets and build predictive models to drive business decisions.");
                job4.setCategory("Data Science");
                job4.setLocation("Remote");
                job4.setSalary(135000.0);
                job4.setExperience("Senior Level");
                job4.setSkillsRequired("Python, SQL, Machine Learning, TensorFlow, Pandas");
                job4.setEmployer(employer);
                jobRepository.save(job4);

                Job job5 = new Job();
                job5.setTitle("DevOps Engineer");
                job5.setDescription("Maintain and improve our cloud infrastructure, CI/CD pipelines, and monitoring systems.");
                job5.setCategory("Engineering");
                job5.setLocation("Austin, TX");
                job5.setSalary(125000.0);
                job5.setExperience("Senior Level");
                job5.setSkillsRequired("AWS, Docker, Kubernetes, Terraform, Jenkins");
                job5.setEmployer(employer);
                jobRepository.save(job5);

                Job job6 = new Job();
                job6.setTitle("Backend Node.js Developer");
                job6.setDescription("Design and implement robust REST APIs using Node.js and Express for our mobile applications.");
                job6.setCategory("Engineering");
                job6.setLocation("London, UK (Remote)");
                job6.setSalary(95000.0);
                job6.setExperience("Entry Level");
                job6.setSkillsRequired("Node.js, Express, MongoDB, REST APIs");
                job6.setEmployer(employer);
                jobRepository.save(job6);

                Job job7 = new Job();
                job7.setTitle("Digital Marketing Manager");
                job7.setDescription("Lead our digital marketing campaigns, oversee SEO strategy, and manage social media channels.");
                job7.setCategory("Marketing");
                job7.setLocation("Chicago, IL");
                job7.setSalary(85000.0);
                job7.setExperience("Mid Level");
                job7.setSkillsRequired("SEO, Google Analytics, Content Strategy, Social Media Management");
                job7.setEmployer(employer);
                jobRepository.save(job7);

                Job job8 = new Job();
                job8.setTitle("QA Automation Engineer");
                job8.setDescription("Develop automated test scripts and ensure the quality of our software releases.");
                job8.setCategory("Quality Assurance");
                job8.setLocation("Remote");
                job8.setSalary(90000.0);
                job8.setExperience("Mid Level");
                job8.setSkillsRequired("Java, Selenium, TestNG, API Testing");
                job8.setEmployer(employer);
                jobRepository.save(job8);

                Job job9 = new Job();
                job9.setTitle("Mobile App Developer");
                job9.setDescription("Build cross-platform mobile applications for iOS and Android using Flutter.");
                job9.setCategory("Engineering");
                job9.setLocation("Toronto, ON");
                job9.setSalary(100000.0);
                job9.setExperience("Mid Level");
                job9.setSkillsRequired("Flutter, Dart, REST APIs, Mobile UI Design");
                job9.setEmployer(employer);
                jobRepository.save(job9);

                Job job10 = new Job();
                job10.setTitle("Cyber Security Analyst");
                job10.setDescription("Monitor network traffic for security events, conduct vulnerability assessments, and mitigate risks.");
                job10.setCategory("Security");
                job10.setLocation("Washington, D.C.");
                job10.setSalary(115000.0);
                job10.setExperience("Senior Level");
                job10.setSkillsRequired("Network Security, Penetration Testing, SIEM, Incident Response");
                job10.setEmployer(employer);
                jobRepository.save(job10);
            }
        };
    }
}
