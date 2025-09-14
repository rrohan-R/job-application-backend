package com.project.JobApplication.repository;

import com.project.JobApplication.model.AppliedJobs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppliedJobsRepository extends JpaRepository<AppliedJobs, Integer> {
    Optional<AppliedJobs> findByJobId(String jobId);
}
