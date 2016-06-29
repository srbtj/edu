package com.syzton.sunread.repository.exam;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.syzton.sunread.model.exam.SpeedQuestion;

public interface SpeedQuestionRepository extends JpaRepository<SpeedQuestion, Long> {
	
	public List<SpeedQuestion> findByArticleId(Long bookId);
	
	public SpeedQuestion findByTopicAndArticleId(String topic,Long articleId);
	 
	public List<SpeedQuestion> findByTopicContaining(String str);
	

}
