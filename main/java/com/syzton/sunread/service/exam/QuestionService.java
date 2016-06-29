package com.syzton.sunread.service.exam;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.syzton.sunread.exception.exam.QuestionNotFoundExcepiton;
import com.syzton.sunread.model.exam.Question;

public interface QuestionService {
	
	public Question add(Question added);

	public Question deleteById(Long id)throws QuestionNotFoundExcepiton;

	public Page<Question> findAll(Pageable pageable);

	public Question findById(Long id) throws QuestionNotFoundExcepiton ;

	public Question update(Question updated) throws QuestionNotFoundExcepiton;
}
