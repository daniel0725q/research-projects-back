package com.quinterodaniel.researchprojects.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@NoArgsConstructor
@Data
public class Advance implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ADVANCE_ID")
    private Long id;

    @Column(name="ADVANCE_TITLE")
    private String title;

    @Column(name="ADVANCE_DESCRIPTION")
    private String description;

    @JsonIgnore
    @ManyToMany(mappedBy="advance")
    private Set<KPI> kpis;
}
