package com.project.JobApplication.controller;

import com.project.JobApplication.model.ApplicationDetails;
import com.project.JobApplication.model.AppliedJobs;
import com.project.JobApplication.repository.ApplicationRepository;
import com.project.JobApplication.service.AppliedJobsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class ApplicationController {

    @Autowired
    private AppliedJobsService appliedJobsService;

    @Autowired
    private ApplicationRepository applicationRepo;

    @PostMapping("/submitApplication")
    public ResponseEntity<?> submitApplication(
            @RequestParam String jobId,
            @RequestParam String title,
            @RequestParam String company,
            @RequestParam String location,
            @RequestParam String description,
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String mobile,
            @RequestParam MultipartFile resume
    ) throws IOException {

        Optional<AppliedJobs> alreadyApplied = appliedJobsService.findByJobId(jobId);
        if (alreadyApplied.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Already applied");
        }


        String uploadDir = System.getProperty("user.dir") + "/uploads/";
        File uploadFolder = new File(uploadDir);
        if (!uploadFolder.exists()) uploadFolder.mkdirs();

        String filePath = uploadDir + UUID.randomUUID() + "-" + resume.getOriginalFilename();
        resume.transferTo(new File(filePath));


        ApplicationDetails details = new ApplicationDetails(0, jobId, title, company, location, description, name, email, mobile, filePath);
        applicationRepo.save(details);


        appliedJobsService.saveAppliedJob(new AppliedJobs(0, jobId, title, company, location, description));

        return ResponseEntity.ok("Application submitted");
    }
}

