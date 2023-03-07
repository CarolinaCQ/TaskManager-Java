package com.project.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Setter
@Getter
@Table(name = "roles")
@SQLDelete(sql = "UPDATE roles SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "title")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name="deleted")
    private Boolean deleted = Boolean.FALSE;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.EAGER)
    private List<User> users;

    @Override
    public String getAuthority() {
        return name;
    }
}
