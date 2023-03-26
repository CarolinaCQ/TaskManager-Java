package com.project.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.EAGER;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Setter
@Getter
@Table(name = "conditions")
@SQLDelete(sql = "UPDATE conditions SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class Condition {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name="deleted")
    private Boolean deleted = Boolean.FALSE;

    @OneToMany(mappedBy = "condition")
    @JsonIgnoreProperties("condition")
    private List<Task> tasks = new ArrayList<>();

}
