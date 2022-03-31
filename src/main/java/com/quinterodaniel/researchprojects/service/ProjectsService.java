package com.quinterodaniel.researchprojects.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.quinterodaniel.researchprojects.dto.ProjectDTO;
import com.quinterodaniel.researchprojects.model.AppUser;
import com.quinterodaniel.researchprojects.model.Project;
import com.quinterodaniel.researchprojects.repo.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProjectsService {
    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ObjectMapper mapper;

    public void deleteProject(Long id) {
        projectRepository.deleteById(id);
    }

    public void createProject(ProjectDTO projectDTO) {
        Project project = mapper.convertValue(projectDTO, Project.class);
        projectRepository.save(project);
    }

    public void updateProject(Project project) {
        projectRepository.save(project);
    }

    public Optional<Project> getProjectById(Long id) {
        return projectRepository.findById(id);
    }

    public List<Project> getProjectsByUser(AppUser user) {
        Set<AppUser> users = new HashSet<>();
        users.add(user);
        return projectRepository.getProjectsByUsersIn(users);
    }

    public List<Project> getProjects() {
        return projectRepository.findAll();
    }
}
