package com.project.JobApplication.service;

import com.project.JobApplication.model.JobDetails;
import com.project.JobApplication.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobService {

    @Autowired
    private JobRepository jobRepository;

    public List<JobDetails> getAllJobs(){
        return jobRepository.findAll();
    }

    public void saveJob(JobDetails jobDetails) {
        jobRepository.save(jobDetails);
    }

}
