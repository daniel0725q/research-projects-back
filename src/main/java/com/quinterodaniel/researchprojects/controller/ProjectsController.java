package com.quinterodaniel.researchprojects.controller;

import com.quinterodaniel.researchprojects.dto.ProjectDTO;
import com.quinterodaniel.researchprojects.model.Project;
import com.quinterodaniel.researchprojects.service.ProjectsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@RestController
public class ProjectsController {
    @Autowired
    private ProjectsService projectsService;

    @GetMapping("/projects/{id}")
    public ResponseEntity getProject(@PathVariable String id) {
        Optional<Project> project = projectsService.getProjectById(Long.parseLong(id));
        if (project.isPresent()) {
            return new ResponseEntity(project.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/projects")
    public ResponseEntity createAdvance(@RequestBody ProjectDTO projectDTO) {
        projectsService.createProject(projectDTO);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping("/projects/{id}")
    public ResponseEntity updateProject(@RequestBody ProjectDTO projectDTO, @PathVariable String id) {
        AtomicReference<ResponseEntity> response = null;
        Optional<Project> projectOptional = projectsService.getProjectById(Long.parseLong(id));
        projectOptional.ifPresentOrElse(project -> {
            project.setDescription(projectDTO.getDescription());
            project.setBudget(projectDTO.getBudget());
            project.setKpis(project.getKpis());
            project.setBeginDate(project.getBeginDate());
            project.setEndDate(project.getEndDate());
            project.setName(project.getName());
            projectsService.updateProject(project);
            response.set(new ResponseEntity(HttpStatus.OK));
        }, () -> {
            response.set(new ResponseEntity(HttpStatus.NOT_FOUND));
        });
        return response.get();
    }

    @DeleteMapping("/projects/{id}")
    public ResponseEntity deleteProject(@PathVariable String id) {
        projectsService.deleteProject(Long.parseLong(id));
        return new ResponseEntity(HttpStatus.OK);
    }
}
