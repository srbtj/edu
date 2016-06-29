package com.syzton.sunread.service.organization;

import java.util.List;
import java.util.Map;

import javassist.NotFoundException;

import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.syzton.sunread.dto.clazz.ClazzSumStatisticDTO;
import com.syzton.sunread.dto.organization.ClazzDTO;
import com.syzton.sunread.model.organization.Campus;
import com.syzton.sunread.model.organization.Clazz;
import com.syzton.sunread.model.user.Student;

/**
 * Created by Morgan-Leon on 2015/3/16.
 */
public interface ClazzService {

    public Clazz add(ClazzDTO Clazz, Long id) throws NotFoundException;

    public Clazz deleteById(Long id) throws NotFoundException;

    public Clazz update(ClazzDTO updated) throws NotFoundException;

    public Clazz findById(Long id) throws NotFoundException;
  
    Page<Clazz> findAll(Pageable pageable) throws NotFoundException;

    Page<Clazz> findByCampus(Long campusId,Pageable pageable) throws NotFoundException;
    
    public List<Student> findAllStudentFromClazz(Long clazzId)throws NotFoundException;
    
    public int getAveragePointsfromClass(Long clazzId) throws NotFoundException;
    
    public int getAverageReadingBookFromClass(Long clazzId) throws NotFoundException;

    public ClazzSumStatisticDTO getSumClazzStatistic(int grade, long campusId) throws NotFoundException;

    List<Clazz> findByGrade(int grade);
    
    List<Clazz> findByCampus(long grade);


	/**
	 * @param clazzName
	 * @return
	 * @throws NotFoundException
	 */
	Clazz findByClazzName(String clazzName) throws NotFoundException;
	
	/**
	 * @param clazzName
	 * @return
	 * @throws NotFoundException
	 */
	Clazz findByClazzNameAndCampus(String clazzName,Campus campus) throws NotFoundException;
	
    public Map<Integer,String> batchSaveOrUpdateClazzFromExcel(Sheet sheet);

    Clazz clazzUpgrade(long clazzId) throws NotFoundException;
}
