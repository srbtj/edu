package com.syzton.sunread.assembler.book;

import org.joda.time.DateTime;

import com.syzton.sunread.dto.book.BookDTO;
import com.syzton.sunread.dto.book.BookExtraDTO;
import com.syzton.sunread.model.book.Book;
import com.syzton.sunread.model.book.BookExtra;

/**
 * Created by jerry on 3/16/15.
 */
public class BookAssembler {

//    public Book fromDTOtoEntireBook(final BookDTO bookDTO,final CategoryRepository categoryRepository){
//
//        Set<Category> categorySet = new HashSet<>();
//
//        for(Long caId: bookDTO.getCategories()){
//            categorySet.add(categoryRepository.findOne(caId));
//        }
//
//        BookExtra extra = this.fromDTOToExtra(bookDTO);
//        extra.setCategories(categorySet);
//
//
//        Book book = this.fromDTOtoBookWithExtra(bookDTO);
//        book.setExtra(extra);
//        return book;
//    }


    public Book fromDTOtoBookWithExtra(final BookDTO bookDTO){



        BookExtra extra = this.fromDTOToExtra(bookDTO.getExtra());

        Book book = new Book();
        fillBook(bookDTO, book);
        book.setExtra(extra);


        return book;
    }

    public void fillBook(BookDTO bookDTO, Book book) {
        book.setName(bookDTO.getName());
        book.setIsbn(bookDTO.getIsbn());
        book.setAuthor(bookDTO.getAuthor());
        book.setPublisher(bookDTO.getPublisher());
        book.setCoin(bookDTO.getCoin());
        book.setDescription(bookDTO.getDescription());
        book.setPoint(bookDTO.getPoint());
        book.setPublicationDate(new DateTime(bookDTO.getPublicationDate()));
        book.setPictureUrl(bookDTO.getPictureUrl());
        book.setWordCount(bookDTO.getWordCount());
        book.setPageCount(bookDTO.getPageCount());
        book.setAuthorIntroduction(bookDTO.getAuthorIntroduction());
        book.setPrice(bookDTO.getPrice());
        book.setHighPrice(bookDTO.getHighPrice());
        book.setEvaluationNum(bookDTO.getEvaluationNum());
        book.setBinding(bookDTO.getBinding());
    }

    public BookExtra fromDTOToExtra(final BookExtraDTO bookExtraDTO){
        BookExtra extra = new BookExtra();
        fillBookExta(bookExtraDTO, extra);
        return extra;
    }

    public void fillBookExta(BookExtraDTO bookExtraDTO, BookExtra extra) {
        extra.setLanguage(bookExtraDTO.getLanguage());
        extra.setLevel(bookExtraDTO.getLevel());
        extra.setLiterature(bookExtraDTO.getLiterature());
        extra.setGrade(bookExtraDTO.getGrade());
        extra.setCategory(bookExtraDTO.getCategory());
        extra.setAgeRange(bookExtraDTO.getAgeRange());
        extra.setPointRange(bookExtraDTO.getPointRange());
    }
}
