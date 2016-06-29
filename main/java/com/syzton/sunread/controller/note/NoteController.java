package com.syzton.sunread.controller.note;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.syzton.sunread.controller.BaseController;
import com.syzton.sunread.dto.common.PageResource;
import com.syzton.sunread.dto.note.NoteDTO;
import com.syzton.sunread.exception.common.NotFoundException;
import com.syzton.sunread.model.note.Note;
import com.syzton.sunread.service.note.NoteService;


/**
 * @author chenty
 *
 */
@Controller
public class NoteController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(NoteController.class);

    private NoteService service;

    @Autowired
    public NoteController(NoteService service) {
        this.service = service;
    }

    @RequestMapping(value = "/api/books/{bookId}/notes", method = RequestMethod.POST)
    @ResponseBody
    public NoteDTO add(@Valid @RequestBody NoteDTO dto, @PathVariable("bookId") Long bookId) {
        LOGGER.debug("Adding a new note entry with information: {}", dto);

        Note added = service.add(dto, bookId);
        
        LOGGER.debug("Added a note entry with information: {}", added);

       return added.createDTO(added);
    }

    @RequestMapping(value = "/api/notes/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public NoteDTO deleteById(@PathVariable("id") Long id) throws NotFoundException {
        LOGGER.debug("Deleting a note entry with id: {}", id);

        Note deleted = service.deleteById(id);
        LOGGER.debug("Deleted note entry with information: {}", deleted);

        return deleted.createDTO(deleted);
    }
    
    @RequestMapping(value = "/api/notes", method = RequestMethod.GET)
    @ResponseBody
    public PageResource<Note> findAll(@RequestParam("page") int page,
	        					 @RequestParam("size") int size,
	        					 @RequestParam("sortBy") String sortBy,
						         @RequestParam("direction") String direction,
						         @RequestParam( value = "searchTerm", required = false) String searchTerm) {
    	Pageable pageable = getPageable(page, size, sortBy, direction);
    	
    	Page<Note> notePage;
    	
    	if (searchTerm != null || searchTerm == ""){
    		notePage = service.findBySearchTerm( pageable, searchTerm );
    	} else {
    		notePage = service.findAll(pageable);
    	}

        return new PageResource<>(notePage, "page", "size");
    }

    @RequestMapping(value = "/api/notes/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public NoteDTO update(@Valid @RequestBody NoteDTO dto, @PathVariable("id") Long noteId) throws NotFoundException {
        LOGGER.debug("Updating a note entry with information: {}", dto);

        Note updated = service.update(dto);
        LOGGER.debug("Updated the information of a note entry to: {}", updated);

        return updated.createDTO(updated);
    }
    
    @RequestMapping(value = "/api/books/{bookId}/notes", method = RequestMethod.GET)
    @ResponseBody
    public PageResource<Note> findNotesByBookId(@PathVariable("bookId") long bookId,
										    	@RequestParam("page") int page,
										        @RequestParam("size") int size,
										        @RequestParam("sortBy") String sortBy,
										        @RequestParam("direction") String direction) {
    	
    	Pageable pageable = getPageable(page, size, sortBy, direction);
		
        Page<Note> notePage = service.findByBookId(pageable, bookId);

        return new PageResource<>(notePage, "page", "size");
    }
    
    @RequestMapping(value = "/api/users/{userId}/notes", method = RequestMethod.GET)
    @ResponseBody
    public PageResource<Note> findNotesByUserId(@PathVariable("userId") long userId,
										    	@RequestParam("page") int page,
										        @RequestParam("size") int size,
										        @RequestParam("sortBy") String sortBy,
										        @RequestParam("direction") String direction) {
    	
    	Pageable pageable = getPageable(page, size, sortBy, direction);
		
        Page<Note> notePage = service.findByUserId(pageable, userId);

        return new PageResource<>(notePage, "page", "size");
    }
    
    @RequestMapping(value = "/api/semesters/{semesterId}/notes", method = RequestMethod.GET)
    @ResponseBody
    public List<Note> findNotesByUserIdAndSemesterId( @RequestParam("userId") long userId,
    											      @PathVariable("semesterId") long semesterId ) {
    	

        List<Note> notes = service.findByUserIdAndSemesterId(userId, semesterId);
        for ( Note note : notes ) {
        	note.setContent(null);
        	note.setComments(null);
        }
        return notes;
    }
}
