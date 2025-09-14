package com.project.JobApplication.repository;

import com.project.JobApplication.model.JobDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JobRepository extends JpaRepository<JobDetails,Integer> {
    
}
