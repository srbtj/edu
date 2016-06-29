package com.syzton.sunread.model.user;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Configurable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.syzton.sunread.model.coinhistory.CoinHistory;
import com.syzton.sunread.model.task.Task;
import com.syzton.sunread.util.DateSerializer;

/**
 * Created by jerry on 3/16/15.
 */
@Entity
@Table(name="student")
@DiscriminatorValue("S")
@Configurable
public class Student extends User{

    public static final int MAX_LENGTH_IDENTITY = 16;

    @Column(length = MAX_LENGTH_IDENTITY)
    private String identity;

    @NotNull
    private long campusId;
    @NotNull
    private long clazzId;
    @NotNull
    private long gradeId;

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER,optional = true)
    @JoinColumn(name="task_id")
    private Task task = new Task();
    
    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER,optional = true)
    @JoinColumn(name = "user_statistic_id")
    private UserStatistic statistic = new UserStatistic();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "student_id")
    private Set<CategoryCount> categoryCount = new HashSet<>();


    @Transient
    private long enrollmentTime; // for receive json

    @JsonIgnore
    @JsonSerialize(using = DateSerializer.class)
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime enrollmentDate; // for insert DB

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "student")
    private Set<CoinHistory> coinHistorySet = new HashSet<>();


    @PrePersist
    public void prePersist(){
        super.prePersist();
        this.enrollmentDate = new DateTime(this.enrollmentTime);
    }


    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }


    public long getEnrollmentTime() {
        return enrollmentTime;
    }

    public void setEnrollmentTime(long enrollmentTime) {
        this.enrollmentTime = enrollmentTime;
    }

    public Set<CategoryCount> getCategoryCount() {
        return categoryCount;
    }

    public void setCategoryCount(Set<CategoryCount> categoryCount) {
        this.categoryCount = categoryCount;
    }

    public Set<CoinHistory> getCoinHistorySet() {
        return coinHistorySet;
    }

    public void setCoinHistorySet(Set<CoinHistory> coinHistorySet) {
        this.coinHistorySet = coinHistorySet;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task)   {
        this.task = task;
    }

    public long getCampusId() {
        return campusId;
    }

    public void setCampusId(long campusId) {
        this.campusId = campusId;
    }

    public long getClazzId() {
        return clazzId;
    }

    public void setClazzId(long clazzId) {
        this.clazzId = clazzId;
    }

    public long getGradeId() {
        return gradeId;
    }

    public void setGradeId(long gradeId)
    {
        this.gradeId = gradeId;
    }

    public UserStatistic getStatistic() {
        return statistic;
    }

    public void setStatistic(UserStatistic statistic) {
        this.statistic = statistic;
    }
}

