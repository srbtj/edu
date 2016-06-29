package com.syzton.sunread.repository.book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.syzton.sunread.model.book.Recommendation;

/**
 * Created by jerry on 3/8/15.
 */
public interface RecommendationRepository extends JpaRepository<Recommendation,Long>,QueryDslPredicateExecutor<Recommendation> {


    public Recommendation findByBookIdAndUserId(Long bookId,Long userId);

}
