package com.quinterodaniel.researchprojects.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.quinterodaniel.researchprojects.dto.KpiDTO;
import com.quinterodaniel.researchprojects.model.KPI;
import com.quinterodaniel.researchprojects.repo.KPIRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class KpiService {
    @Autowired
    private KPIRepository kpiRepository;

    @Autowired
    private ObjectMapper mapper;

    public List<KPI> getKpiByProjectId(Long id) {
        return kpiRepository.findByProjectId(id);
    }

    public void deleteKPI(Long id) {
        kpiRepository.deleteById(id);
    }

    public void createKpi(KpiDTO kpiDTO) {
        KPI kpi = mapper.convertValue(kpiDTO, KPI.class);
        kpiRepository.save(kpi);
    }

    public void updateKpi(KPI advance) {
        kpiRepository.save(advance);
    }

    public Optional<KPI> getKpiById(Long id) {
        return kpiRepository.findById(id);
    }
}
