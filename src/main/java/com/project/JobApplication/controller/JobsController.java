package com.project.JobApplication.controller;

import com.project.JobApplication.model.AppliedJobs;
import com.project.JobApplication.model.JobDetails;
import com.project.JobApplication.model.SavedJobs;
import com.project.JobApplication.service.AppliedJobsService;
import com.project.JobApplication.service.JobService;
import com.project.JobApplication.service.SavedJobsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class JobsController {

    @Autowired
    private JobService jobService;

    @Autowired
    private AppliedJobsService appliedJobsService;

    @Autowired
    private SavedJobsService savedJobsService;

    @GetMapping("/getJobs")
    public List<JobDetails> getAllJobs() {
        return jobService.getAllJobs();
    }

    @PostMapping("/applyJob")
    public ResponseEntity<String> saveAppliedJob(@RequestBody AppliedJobs appliedJobs) {
        Optional<AppliedJobs> existing = appliedJobsService.findByJobId(appliedJobs.getJobId());
        if (existing.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Job already applied");
        }

        appliedJobsService.saveAppliedJob(appliedJobs);


        savedJobsService.deleteByJobId(appliedJobs.getJobId());

        return ResponseEntity.ok("Job applied successfully");
    }



    @GetMapping("/getSavedJobs")
    public List<SavedJobs> getSavedJobs() {
        return savedJobsService.getSavedJobs();
    }

    @PostMapping("/saveSavedJob")
    public void saveSavedJob(@RequestBody SavedJobs savedJobs) {
        savedJobsService.saveSavedJob(savedJobs);
    }

    @DeleteMapping("/unsaveJob/{jobId}")
    public ResponseEntity<String> unsaveJobByJobId(@PathVariable String jobId) {
        Optional<SavedJobs> job = savedJobsService.findByJobId(jobId);
        if (job.isPresent()) {
            savedJobsService.deleteSavedJob(job.get().getId());
            return ResponseEntity.ok("Job unsaved successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Saved job not found");
        }
    }

    @GetMapping("/isJobSaved/id/{jobId}")
    public boolean isJobSaved(@PathVariable String jobId) {
        return savedJobsService.isJobSaved(jobId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/postJob")
    public void postJob(@RequestBody JobDetails jobDetails) {
        jobService.saveJob(jobDetails);
    }


}
