package com.project.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

import static jakarta.persistence.FetchType.EAGER;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Setter
@Getter
@Table(name = "projects")
@SQLDelete(sql = "UPDATE projects SET deleted = true WHERE id = ?")
public class Project {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @CreationTimestamp
    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @UpdateTimestamp
    @Column(name = "update_date")
    private LocalDateTime updateDate;

    @Column(name = "deleted")
    private Boolean deleted;

    /*@OneToMany(fetch = EAGER)
    @JoinTable(name = "tasks",
            joinColumns = @JoinColumn(name = "id"))
    @JsonIgnoreProperties("project")
    private List<Task> tasks;

    @ManyToOne
    @JsonIgnoreProperties("projects")
    private User user;*/

}
