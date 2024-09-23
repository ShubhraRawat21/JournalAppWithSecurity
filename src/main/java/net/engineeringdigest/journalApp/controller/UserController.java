package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.Entity.User;
import net.engineeringdigest.journalApp.apiResponse.WeatherResponse;
import net.engineeringdigest.journalApp.repository.UserRepository;
import net.engineeringdigest.journalApp.service.UserService;
import net.engineeringdigest.journalApp.service.WeatherService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/secureUser")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    WeatherService weatherService;

    @GetMapping
    public List<User> getAllUsers(){
      return userService.getAll();
    }

    @GetMapping("/id")
    public ResponseEntity<?> GetUserByID() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        WeatherResponse weatherResponse = weatherService.getWheather("Mumbai");
        int tempfeelsLike = 0;
        if (weatherResponse != null) {
            tempfeelsLike = weatherResponse.getCurrent().getFeelslike();
        }
        return new ResponseEntity<>("Hi:" + authentication.getName() + "temp feels like" + tempfeelsLike, HttpStatus.OK);
    }
   @PostMapping("/savenew")
   public void createSecuredUser(@RequestBody  User user){
        userService.saveNewUser(user);
    }

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User user){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User userinDB = userService.findByUserName(userName);
        if(userinDB != null){
            userinDB.setUserName(user.getUserName());
            userinDB.setPassword(user.getPassword());
            userService.saveNewUser(userinDB);
            return new ResponseEntity<>(userinDB, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userRepository.deleteByUserName(authentication.getName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}