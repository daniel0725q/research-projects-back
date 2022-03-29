package com.quinterodaniel.researchprojects.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@Data
public class Project implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PROJECT_ID")
    private Long id;

    @Column(name="PROJECT_NAME")
    private String name;

    @Column(name="PROJECT_DESC")
    private String description;

    @Column(name="PROJECT_BUDGET")
    private float budget;

    @Column(name = "BEGIN_DATE", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    protected Date beginDate;

    @Column(name = "END_DATE", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    protected Date endDate;

    @Column(name = "created_date", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    protected Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    protected Date modifiedDate;

    @JsonIgnore
    @ManyToMany(mappedBy = "projects")
    private List<AppUser> users;

    @JsonIgnore
    @OneToMany
    @JoinTable(name = "project_kpi", joinColumns = {@JoinColumn(name = "PROJECT_ID")}, inverseJoinColumns = {@JoinColumn(name = "KPI_ID")})
    private Set<KPI> kpis;
}
