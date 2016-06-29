package com.syzton.sunread.controller.book;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.syzton.sunread.model.book.Dictionary;
import com.syzton.sunread.model.book.DictionaryType;
import com.syzton.sunread.service.book.DictionaryService;

/**
 * Created by jerry on 3/21/15.
 */
@Controller
@RequestMapping("/api")
public class DictionaryController {

    private DictionaryService dictionaryService;

    @Autowired
    public DictionaryController(DictionaryService dictionaryService) {
        this.dictionaryService = dictionaryService;
    }

    @RequestMapping(value = "/dictionaries",method = RequestMethod.POST)
    @ResponseBody
    public Dictionary add(@Valid @RequestBody Dictionary dictionary){

        return dictionaryService.add(dictionary);
    }


    @RequestMapping(value = "/dictionaries",method = RequestMethod.GET)
    @ResponseBody
    public List<Dictionary> findDictionariesByType(@RequestParam DictionaryType type){
        return dictionaryService.findByType(type);
    }


}
