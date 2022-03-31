package com.quinterodaniel.researchprojects.repo;

import com.quinterodaniel.researchprojects.model.KPI;
import com.quinterodaniel.researchprojects.model.Project;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KPIRepository extends CrudRepository<KPI, Long> {
    List<KPI> findByProject(Project project);

    List<KPI> findByProjectId(Long id);
}
