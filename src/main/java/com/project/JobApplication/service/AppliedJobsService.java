package com.project.JobApplication.service;

import com.project.JobApplication.model.AppliedJobs;
import com.project.JobApplication.repository.AppliedJobsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AppliedJobsService {

    @Autowired
    private AppliedJobsRepository appliedJobsRepository;

    public void saveAppliedJob(AppliedJobs appliedJobs) {
        appliedJobsRepository.save(appliedJobs);
    }

    public List<AppliedJobs> getAllAppliedJobs() {
        return appliedJobsRepository.findAll();
    }

    public Optional<AppliedJobs> findByJobId(String jobId) {
        return appliedJobsRepository.findByJobId(jobId);
    }
}
