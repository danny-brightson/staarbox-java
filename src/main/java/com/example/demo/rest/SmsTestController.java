package com.example.demo.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.repo.UserRepository;
import com.example.demo.service.SmsNotificationService;
import com.example.demo.entity.User;

@RestController
@RequestMapping("/sms")
public class SmsTestController {

    @Autowired
    private SmsNotificationService service;

    @PostMapping("/test")
    public String testSms() {
        service.sendSmartSmsToAllUsers("Test message from API", "TEST");
        return "SMS triggered successfully";
    }
    @RestController
    @RequestMapping("/user")
    public class UserController {

        @Autowired
        private UserRepository repo;

        @PostMapping("/add")
        public User addUser(@RequestBody User user) {
            return repo.save(user);
        }
    }
}