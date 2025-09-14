package com.project.JobApplication.repository;

import com.project.JobApplication.model.ApplicationDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationRepository extends JpaRepository<ApplicationDetails,Integer> {

}
