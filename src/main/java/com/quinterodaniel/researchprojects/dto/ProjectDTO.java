package com.quinterodaniel.researchprojects.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ProjectDTO {
    private String name;

    private String description;

    private float budget;

    protected Date beginDate;

    protected Date endDate;
}
