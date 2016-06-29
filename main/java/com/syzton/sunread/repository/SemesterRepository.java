package com.syzton.sunread.repository;

import java.util.ArrayList;

import org.joda.time.DateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import com.syzton.sunread.model.organization.Campus;
import com.syzton.sunread.model.semester.Semester;

public interface SemesterRepository extends JpaRepository<Semester,Long>,QueryDslPredicateExecutor<Semester>{
	
	@Query("SELECT Distinct(s) FROM Semester s WHERE s.campus=(:campus) AND s.startTime<=(:time) AND s.endTime>(:time)")
	Semester findByTimeAndCampus(@Param("time")DateTime time,@Param("campus")Campus campus);
	@Query("SELECT Distinct(s) FROM Semester s WHERE s.campus=(:campus) AND s.startTime>=(:startTime) AND s.startTime<(:endTime) ORDER BY s.startTime DESC ")
	ArrayList<Semester> findByDuration(@Param("startTime")DateTime startTime,@Param("endTime")DateTime endTime,@Param("campus")Campus campus);
	
	Page<Semester> findByCampus(Campus campus, Pageable pageable);
}
