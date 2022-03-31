package com.quinterodaniel.researchprojects.dto;

import com.quinterodaniel.researchprojects.model.KPI;
import lombok.Data;

import java.util.Set;

@Data
public class AdvanceDTO {
    private String title;

    private String description;

    private Set<KPI> kpis;
}
