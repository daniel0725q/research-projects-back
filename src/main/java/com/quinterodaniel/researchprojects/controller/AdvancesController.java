package com.quinterodaniel.researchprojects.controller;

import com.quinterodaniel.researchprojects.dto.AdvanceDTO;
import com.quinterodaniel.researchprojects.model.Advance;
import com.quinterodaniel.researchprojects.service.AdvancesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@RestController
public class AdvancesController {
    @Autowired
    private AdvancesService advancesService;

    @GetMapping("/advances/{id}")
    public ResponseEntity getAdvance(@PathVariable String id) {
        Optional<Advance> advance = advancesService.getAdvanceById(Long.parseLong(id));
        if (advance.isPresent()) {
            return new ResponseEntity(advance.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/advances")
    public ResponseEntity createAdvance(@RequestBody AdvanceDTO advanceDTO) {
        advancesService.createAdvance(advanceDTO);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping("/advances/{id}")
    public ResponseEntity updateAdvance(@RequestBody AdvanceDTO advanceDTO, @PathVariable String id) {
        AtomicReference<ResponseEntity> response = null;
        Optional<Advance> advanceOptional = advancesService.getAdvanceById(Long.parseLong(id));
        advanceOptional.ifPresentOrElse(advance -> {
            advance.setDescription(advanceDTO.getDescription());
            advance.setTitle(advanceDTO.getTitle());
            advance.setKpis(advanceDTO.getKpis());
            advancesService.updateAdvance(advance);
            response.set(new ResponseEntity(HttpStatus.OK));
        }, () -> {
            response.set(new ResponseEntity(HttpStatus.NOT_FOUND));
        });
        return response.get();
    }

    @DeleteMapping("/advances/{id}")
    public ResponseEntity deleteAdvance(@PathVariable String id) {
        advancesService.deleteAdvance(Long.parseLong(id));
        return new ResponseEntity(HttpStatus.OK);
    }
}
