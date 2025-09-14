package com.project.JobApplication.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class AppliedJobs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String jobId;
    private String title;
    private String company;
    private String location;
    private String description;
}
