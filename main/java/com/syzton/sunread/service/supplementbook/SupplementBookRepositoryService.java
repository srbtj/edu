package com.syzton.sunread.service.supplementbook;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.syzton.sunread.dto.supplementbook.SupplementBookDTO;
import com.syzton.sunread.exception.common.NotFoundException;
import com.syzton.sunread.model.book.Book;
import com.syzton.sunread.model.supplementbook.SupplementBook;
import com.syzton.sunread.repository.book.BookRepository;
import com.syzton.sunread.repository.supplementbook.SupplementBookRepository;

/**
 */
@Service
public class SupplementBookRepositoryService implements SupplementBookService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SupplementBookRepositoryService.class);
    private SupplementBookRepository repository;
    private BookRepository bookRepository;

    @Autowired
    public SupplementBookRepositoryService(SupplementBookRepository repository,BookRepository bookRepository) {
        this.repository = repository;
        this.bookRepository = bookRepository;
    }

    @Override
    public SupplementBook add(SupplementBookDTO added) {
        LOGGER.debug("Adding a new SupplementBook entry with information: {}", added);
        DateTime date = new DateTime(added.getPublicationDate());
        
        Book book = bookRepository.findByIsbn(added.getIsbn());
        if(book == null){
	        SupplementBook bookModel = SupplementBook.getBuilder(added.getLanguage()
	        		,added.getAuthor(),added.getPublisher(),date
	        		,added.getIsbn(), added.getName())
	                .description(added.getDescription())
	                .build();
	        return repository.save(bookModel);
        }
        else{
	        SupplementBook bookModel = SupplementBook.getBuilder(added.getLanguage()
	        		,added.getAuthor(),added.getPublisher(),date
	        		,"", added.getName())
	                .description(added.getDescription())
	                .build();
	        return bookModel;
        }
    }

    @Transactional(readOnly = true, rollbackFor = {NotFoundException.class})
    @Override
    public SupplementBook findById(Long id) throws NotFoundException {
        LOGGER.debug("Finding a book entry with id: {}", id);

        SupplementBook found = repository.findOne(id);
        LOGGER.debug("Found book entry: {}", found);

        if (found == null) {
            throw new NotFoundException("No book found with id: " + id);
        }

        return found;
    }

    @Transactional(rollbackFor = {NotFoundException.class})
    @Override
    public SupplementBook deleteById(Long id) throws NotFoundException {
        LOGGER.debug("Deleting a to-do entry with id: {}", id);

        SupplementBook deleted = findById(id);
        LOGGER.debug("Deleting to-do entry: {}", deleted);

        repository.delete(deleted);
        return deleted;
    }
    @Transactional(rollbackFor = {NotFoundException.class})
    @Override
    public Page<SupplementBook> findAll(Pageable pageable) throws NotFoundException{

        Page<SupplementBook> bookPages = repository.findAll(pageable);

        return bookPages;

    }


}
