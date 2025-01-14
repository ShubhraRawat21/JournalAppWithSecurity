package net.engineeringdigest.journalApp;

import net.engineeringdigest.journalApp.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class UserServiceTests {
    @Autowired
   private UserRepository userRepository;
    @Test
    public void testFindByUserName(){
       assertNotNull(userRepository.findByUserName("Shyam"));
    }
}
