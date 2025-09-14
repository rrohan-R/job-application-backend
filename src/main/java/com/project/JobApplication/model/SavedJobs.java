package com.project.JobApplication.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class SavedJobs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String jobId;
    private String title;
    private String company;
    private String location;
    private String description;

}
