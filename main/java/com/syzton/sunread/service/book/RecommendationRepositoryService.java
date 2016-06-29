package com.syzton.sunread.service.book;

import static com.syzton.sunread.repository.book.predicates.RecommendationPredicates.countRMDuringMonthly;
import static com.syzton.sunread.repository.book.predicates.RecommendationPredicates.countRMDuringWeekly;
import static com.syzton.sunread.repository.book.predicates.RecommendationPredicates.countRMDuringYearly;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.syzton.sunread.exception.common.DuplicateException;
import com.syzton.sunread.exception.common.NotFoundException;
import com.syzton.sunread.model.book.Book;
import com.syzton.sunread.model.book.Recommendation;
import com.syzton.sunread.model.user.User;
import com.syzton.sunread.repository.book.BookRepository;
import com.syzton.sunread.repository.book.RecommendationRepository;
import com.syzton.sunread.repository.user.UserRepository;

/**
 * Created by jerry on 3/18/15.
 */
@Service
public class RecommendationRepositoryService implements RecommendationService {

    private BookRepository bookRepository;

    private RecommendationRepository recommendationRepository;

    private UserRepository userRepository;
    @Autowired
    public RecommendationRepositoryService(BookRepository bookRepository, RecommendationRepository recommendationRepository, UserRepository userRepository) {
        this.bookRepository = bookRepository;
        this.recommendationRepository = recommendationRepository;
        this.userRepository = userRepository;
    }



    @Transactional
    @Override
    public void recommend(Long bookId,Long userId) {

        Book book = bookRepository.findOne(bookId);

        if(book == null)
            throw new NotFoundException("book with id:"+ bookId +" entity dose not found..");
        User user = userRepository.findOne(userId);
        if(user == null)
            throw new NotFoundException("user with id:"+ userId +" entity dose not found..");
        Recommendation exitsWithUser = recommendationRepository.findByBookIdAndUserId(bookId, userId);
        if(exitsWithUser != null){
            throw new DuplicateException("userId = "+userId+" is already recommended before...");
        }

        Recommendation recommendation = new Recommendation();
        recommendation.setBookId(bookId);
        recommendation.setUserId(userId);


        recommendationRepository.save(recommendation);

        book.getStatistic().increaseRecommends();

        long weekRM = recommendationRepository.count(countRMDuringWeekly(bookId));

        book.getStatistic().setWeeklyRecommend(weekRM);

        long monthRM = recommendationRepository.count(countRMDuringMonthly(bookId));

        book.getStatistic().setMonthlyRecommend(monthRM);

        long yearRM = recommendationRepository.count(countRMDuringYearly(bookId));
        book.getStatistic().setYearlyRecommend(yearRM);
        bookRepository.save(book);


    }

}
