package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.Entity.User;
import net.engineeringdigest.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UsecuredUserController {
    @Autowired
    public UserService userService;
    @PostMapping
    public void createUser(@RequestBody User user){
        userService.saveEntry(user);
    }

    @PostMapping("/userwithdetails")
    public void createSecuredUser(@RequestBody  User user){
        userService.saveNewUser(user);
    }

}


