package com.quinterodaniel.researchprojects.dto;

import com.quinterodaniel.researchprojects.model.Advance;
import com.quinterodaniel.researchprojects.model.Project;
import lombok.Data;

import java.util.List;

@Data
public class KpiDTO {
    private String name;

    private Project project;

    private List<Advance> advance;
}
