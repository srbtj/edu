package com.syzton.sunread.model.user;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * Created by jerry on 3/16/15.
 */
@Entity
@Table(name="parent")
@DiscriminatorValue("P")
public class Parent extends User{

    private String workUnit;


    @ManyToMany
    @JoinTable(name="parent_student",
            joinColumns = @JoinColumn(name="parent_id", referencedColumnName="id"),
            inverseJoinColumns = @JoinColumn(name="student_id", referencedColumnName="id")
    )
    private Set<Student> children = new HashSet<>();

    public String getWorkUnit() {
        return workUnit;
    }

    public void setWorkUnit(String workUnit) {
        this.workUnit = workUnit;
    }

    public Set<Student> getChildren() {
        return children;
    }

    public void setChildren(Set<Student> children) {
        this.children = children;
    }
}

