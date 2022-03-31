package com.quinterodaniel.researchprojects.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.quinterodaniel.researchprojects.dto.AdvanceDTO;
import com.quinterodaniel.researchprojects.model.Advance;
import com.quinterodaniel.researchprojects.model.KPI;
import com.quinterodaniel.researchprojects.model.Project;
import com.quinterodaniel.researchprojects.repo.AdvanceRepository;
import com.quinterodaniel.researchprojects.repo.KPIRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AdvancesService {
    @Autowired
    private AdvanceRepository advanceRepository;

    @Autowired
    private KPIRepository kpiRepository;

    @Autowired
    private ObjectMapper mapper;

    public List<Advance> getAdvancesByKPI(KPI kpi) {
        return advanceRepository.findAdvanceByKpis(kpi);
    }

    public Set<Advance> getAdvancesByProject(Project project) {
        List<KPI> kpisByProject = kpiRepository.findByProject(project);
        Set<Advance> advances = new HashSet<>();
        for (KPI kpi : kpisByProject) {
            List<Advance> advancesByKpi = getAdvancesByKPI(kpi);
            advances.addAll(advancesByKpi);
        }
        return advances;
    }

    public void deleteAdvance(Long id) {
        advanceRepository.deleteAdvanceById(id);
    }

    public void createAdvance(AdvanceDTO advanceDTO) {
        Advance advance = mapper.convertValue(advanceDTO, Advance.class);
        advanceRepository.save(advance);
    }

    public void updateAdvance(Advance advance) {
        advanceRepository.save(advance);
    }

    public Optional<Advance> getAdvanceById(Long id) {
        return advanceRepository.findById(id);
    }
}
