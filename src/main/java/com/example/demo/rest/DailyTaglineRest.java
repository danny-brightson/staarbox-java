package com.example.demo.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.DailyTagLine;
import com.example.demo.entity.FaqQuestions;
import com.example.demo.repo.DailyTaglineRepo;
import com.example.demo.repo.FaqQuestionsRepository;

@RestController
@RequestMapping("/api")
public class DailyTaglineRest {


    @Autowired
    private FaqQuestionsRepository repository;
	    @Autowired
	    private  DailyTaglineRepo  dailyTaglineRepo;
	    
	    
	    @GetMapping("/dailyTagLine")
	    public List<DailyTagLine> getDailyTaglin() {
	        return dailyTaglineRepo.findAll();
	    }
	
	    @GetMapping("/faqs")
	    public List<FaqQuestions> getFaqs() {
	        return repository.findByStatusId(1L);
	    }

}
