package com.project.JobApplication.service;

import com.project.JobApplication.model.SavedJobs;
import com.project.JobApplication.repository.SavedJobsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SavedJobsService {

    @Autowired
    private SavedJobsRepository savedJobsRepository;

    public void saveSavedJob(SavedJobs savedJobs) {
        savedJobsRepository.save(savedJobs);
    }

    public List<SavedJobs> getSavedJobs() {
        return savedJobsRepository.findAll();
    }

    public void deleteSavedJob(int id) {
        savedJobsRepository.deleteById(id); // deletes using internal primary key
    }

    public void deleteByJobId(String jobId) {
        Optional<SavedJobs> job = savedJobsRepository.findByJobId(jobId);
        job.ifPresent(savedJobsRepository::delete);
    }

    public boolean isJobSaved(String jobId) {
        return savedJobsRepository.findByJobId(jobId).isPresent();
    }

    public Optional<SavedJobs> findByJobId(String jobId) {
        return savedJobsRepository.findByJobId(jobId);
    }


}
