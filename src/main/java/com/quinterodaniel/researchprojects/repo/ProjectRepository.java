package com.quinterodaniel.researchprojects.repo;

import com.quinterodaniel.researchprojects.model.AppUser;
import com.quinterodaniel.researchprojects.model.Project;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ProjectRepository extends CrudRepository<Project, Long> {
    List<Project> getProjectsByUsersIn(Set<AppUser> users);

}
