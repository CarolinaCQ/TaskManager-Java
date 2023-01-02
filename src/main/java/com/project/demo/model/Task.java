package com.project.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;
import java.util.List;

import static jakarta.persistence.FetchType.EAGER;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Setter
@Getter
@Table(name = "tasks")
@SQLDelete(sql = "UPDATE tasks SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class Task {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    Long id;

    @NotNull
    @Column(name = "title")
    String title;

    @Column(name = "description")
    String description;

    @NotNull
    @Column(name = "duration")
    Double duration;

    @Column(name = "finish_date")
    LocalDateTime finishDate;

    @CreationTimestamp
    @Column(name = "creation_date")
    LocalDateTime creationDate;

    @UpdateTimestamp
    @Column(name = "update_date")
    LocalDateTime updateDate;

    @Column(name = "deleted")
    Boolean deleted;

    @Column(name = "condition")
    private Condition condition;

    /*@OneToMany
    @JoinTable(name = "subtasks",
            joinColumns = @JoinColumn(name = "id"))
    private List<Subtask> subtasks;*/

    @ManyToOne(fetch = EAGER)
    @JoinColumn(name = "id")
    @JsonIgnoreProperties("tasks")
    private Project project;
}
