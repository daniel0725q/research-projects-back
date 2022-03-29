package com.quinterodaniel.researchprojects.repo;

import com.quinterodaniel.researchprojects.model.Project;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends CrudRepository<Project, Long> {
}
