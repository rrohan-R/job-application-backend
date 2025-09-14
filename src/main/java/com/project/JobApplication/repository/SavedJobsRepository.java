package com.project.JobApplication.repository;

import com.project.JobApplication.model.SavedJobs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SavedJobsRepository extends JpaRepository<SavedJobs, Integer> {
    Optional<SavedJobs> findByJobId(String jobId);
}
