package com.quinterodaniel.researchprojects.controller;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.quinterodaniel.researchprojects.dto.KpiDTO;
import com.quinterodaniel.researchprojects.model.KPI;
import com.quinterodaniel.researchprojects.service.KpiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@RestController
public class KpiController {
    @Autowired
    private KpiService kpiService;

    @Autowired
    private ObjectMapper mapper;

    @GetMapping("/kpi/{id}")
    public ResponseEntity getKpi(@PathVariable String id) {
        Optional<KPI> kpi = kpiService.getKpiById(Long.parseLong(id));
        if (kpi.isPresent()) {
            return new ResponseEntity(kpi.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/kpi")
    public ResponseEntity createKpi(@RequestBody KpiDTO kpiDTO) {
        kpiService.createKpi(kpiDTO);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping("/kpi/{id}")
    public ResponseEntity updateKpi(@RequestBody KpiDTO kpiDTO, @PathVariable String id) {
        AtomicReference<ResponseEntity> response = null;
        Optional<KPI> kpiOptional = kpiService.getKpiById(Long.parseLong(id));
        kpiOptional.ifPresentOrElse(kpi -> {
            try {
                mapper.updateValue(kpiDTO, kpi);
                kpiService.updateKpi(kpi);
                response.set(new ResponseEntity(HttpStatus.OK));
            } catch (JsonMappingException e) {
                response.set(new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR));
            }
        }, () -> response.set(new ResponseEntity(HttpStatus.NOT_FOUND)));
        return response.get();
    }

    @DeleteMapping("/kpi/{id}")
    public ResponseEntity deleteKpi(@PathVariable String id) {
        kpiService.deleteKPI(Long.parseLong(id));
        return new ResponseEntity(HttpStatus.OK);
    }
}
