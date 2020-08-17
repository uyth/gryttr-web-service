package com.uyth.gryttr.model;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "collections")
@EntityListeners(AuditingEntityListener.class)
public class Collection {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "collection")
    private Set<Boulder> boulders;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Boulder> getBoulders() {
        return boulders;
    }

    public void setBoulders(Set<Boulder> boulders) {
        this.boulders = boulders;
    }

    public Collection addBoulder(Boulder boulder) {
        boulder.setCollection(this);
        this.getBoulders().add(boulder);
        return this;
    }
}
