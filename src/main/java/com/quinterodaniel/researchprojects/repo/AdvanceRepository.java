package com.quinterodaniel.researchprojects.repo;

import com.quinterodaniel.researchprojects.model.Advance;
import com.quinterodaniel.researchprojects.model.KPI;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdvanceRepository extends CrudRepository<Advance, Long> {
    List<Advance> findAdvanceByKpis(KPI kpi);

    void deleteAdvanceById(Long id);

}
