package com.syzton.sunread.controller.tag;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import com.syzton.sunread.dto.tag.TagStatisticsDTO;
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
import com.syzton.sunread.dto.tag.BookTagDTO;
import com.syzton.sunread.exception.tag.BookTagNotFoundException;
import com.syzton.sunread.model.tag.BookTag;
import com.syzton.sunread.service.tag.BookTagService;


/**
 * @author chenty
 *
 */
@Controller
public class BookTagController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookTagController.class);

    private BookTagService service;

    @Autowired
    public BookTagController(BookTagService service) {
        this.service = service;
    }
    
    @RequestMapping(value = "/api/booktags", method = RequestMethod.POST)
    @ResponseBody
    public BookTagDTO add(@Valid @RequestBody BookTagDTO dto) {
        LOGGER.debug("Adding a new booktag entry with information: {}", dto);

        BookTag added = service.add(dto);

        LOGGER.debug("Added a booktag entry with information: {}", added);

       return added.createDTO(added);
    }

    @RequestMapping(value = "/api/booktags/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public BookTagDTO deleteById(@PathVariable("id") Long id) throws BookTagNotFoundException {
        LOGGER.debug("Deleting a booktag entry with id: {}", id);

        BookTag deleted = service.deleteById(id);
        LOGGER.debug("Deleted booktag entry with information: {}", deleted);

        return deleted.createDTO(deleted);
    }
    
    @RequestMapping(value = "/api/booktags", method = RequestMethod.GET)
    @ResponseBody
    public List<BookTagDTO> findAll() {
        LOGGER.debug("Finding all tag entries.");

        List<BookTag> models = service.findAll();
        LOGGER.debug("Found {} booktag entries.", models.size());

        return createDTOs(models);
    }

    @RequestMapping(value = "/api/tags/{tagId}/booktags", method = RequestMethod.GET)
    @ResponseBody
    public PageResource<BookTag> findBookTagsByBookId(@PathVariable("tagId") long tagId,
										    		  @RequestParam("page") int page,
										              @RequestParam("size") int size,
										              @RequestParam("sortBy") String sortBy,
										              @RequestParam("direction") String direction) {

    	Pageable pageable = getPageable(page, size, sortBy, direction);
    	
        Page<BookTag> bookTagPage = service.findByTagId(pageable, tagId);

        return new PageResource<>(bookTagPage, "page", "size");
    }
    @RequestMapping(value = "/api/tags/statistics", method = RequestMethod.GET)
    @ResponseBody
    public List<TagStatisticsDTO> statistics() {


        return service.statistics();

    }

    private List<BookTagDTO> createDTOs(List<BookTag> models) {
        List<BookTagDTO> dtos = new ArrayList<BookTagDTO>();

        for (BookTag model: models) {
            dtos.add(model.createDTO(model));
        }
        return dtos;
    }   
}
