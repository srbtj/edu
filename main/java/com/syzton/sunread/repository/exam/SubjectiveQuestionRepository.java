package com.syzton.sunread.repository.exam;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.syzton.sunread.dto.exam.SubjectQuestionWithBookName;
import com.syzton.sunread.model.exam.ObjectiveQuestion;
import com.syzton.sunread.model.exam.SubjectiveQuestion;
import com.syzton.sunread.model.exam.SubjectiveQuestion.SubjectiveQuestionType;

public interface SubjectiveQuestionRepository extends JpaRepository<SubjectiveQuestion, Long>{
	
	public List<SubjectiveQuestion> findByQuestionTypeAndBookId(SubjectiveQuestionType questionType,Long bookId );
	
	public SubjectiveQuestion findByTopicAndBookIdAndQuestionType(String content,Long bookId,SubjectiveQuestionType questionType);
	
	public Page<SubjectiveQuestion> findByTopicContaining(String topic,Pageable pageable);
}
