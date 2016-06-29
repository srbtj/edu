package com.syzton.sunread.repository.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.syzton.sunread.model.region.SchoolDistrict;
import com.syzton.sunread.model.user.Teacher;

/**
 * Created by jerry on 3/16/15.
 */
@Repository
public interface TeacherRepository extends JpaRepository<Teacher,Long>{

    Page<Teacher> findByCampusId(Long campusId,Pageable pageable);
    
    public Page<Teacher> findByUserIdContainingOrUsernameContainingAndCampusId(String id,String name,Long campusId,Pageable pageable);

	public Teacher findByUserId(String userId);

}
