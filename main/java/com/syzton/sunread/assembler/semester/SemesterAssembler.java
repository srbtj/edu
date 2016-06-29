package com.syzton.sunread.assembler.semester;

import org.joda.time.DateTime;

import com.syzton.sunread.dto.semester.SemesterDTO;
import com.syzton.sunread.model.organization.Campus;
import com.syzton.sunread.model.semester.Semester;
/*
 * @Author Morgan-Leon
 * @Date 2015-3-22
 */
public class SemesterAssembler {
	
	 public Semester fromDTOtoModel(final SemesterDTO DTO){
		Semester semester = new Semester();
		semester.setSemester(DTO.getSemester());
		semester.setStartTime(new DateTime(DTO.getStartTime()));
		semester.setEndTime(new DateTime(DTO.getEndTime()));
		semester.setDescription(DTO.getDescription());			
		return semester; 
	 }
	
	 public Semester fromDTOtoModel(final SemesterDTO DTO,Campus campus){
		Semester semester = new Semester();
		semester.setSemester(DTO.getSemester());
		semester.setStartTime(new DateTime(DTO.getStartTime()));
		semester.setEndTime(new DateTime(DTO.getEndTime()));
		semester.setDescription(DTO.getDescription());	
		semester.setCampus(campus);		
		return semester; 
	 }
	 
}
