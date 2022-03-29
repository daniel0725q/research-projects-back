package com.quinterodaniel.researchprojects.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@NoArgsConstructor
@Data
public class KPI implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "KPI_ID")
    private Long id;

    @Column(name = "KPI_NAME")
    private String name;

    @Column(name = "created_date", nullable=false, updatable=false)
    @Temporal(TemporalType.TIMESTAMP)
    protected Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    protected Date modifiedDate;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="PROJECT_ID", nullable=false)
    private Project project;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="ADVANCE_ID", nullable=false)
    private Advance advance;
}
