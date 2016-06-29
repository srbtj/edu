package com.syzton.sunread.model.task;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.syzton.sunread.model.common.AbstractEntity;
import com.syzton.sunread.model.user.Student;

/**
 * Created by jerry on 3/28/15.
 */
@Entity
public class Task extends AbstractEntity implements Serializable{
	
    private long studentId;
	
    private long teacherId;

    private long semesterId;

    private int targetBookNum;

    private int targetPoint;

    public int getTargetBookNum() {
        return targetBookNum;
    }

    public void setTargetBookNum(int targetBookNum) {
        this.targetBookNum = targetBookNum;
    }

    public int getTargetPoint() {
        return targetPoint;
    }

    public void setTargetPoint(int targetPoint) {
        this.targetPoint = targetPoint;
    }

    public long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(long teacherId) {
        this.teacherId = teacherId;
    }

    public long getSemesterId() {
        return semesterId;
    }

    public void setSemesterId(long semesterId) {
        this.semesterId = semesterId;
    }

    public Long getStudentId() {
        return this.studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }
}
