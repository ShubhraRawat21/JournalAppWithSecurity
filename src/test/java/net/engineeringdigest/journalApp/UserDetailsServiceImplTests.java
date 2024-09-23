package net.engineeringdigest.journalApp;

import net.engineeringdigest.journalApp.repository.UserRepository;
import net.engineeringdigest.journalApp.service.CustomUserDetailsServiceImplForSecurity;
import net.engineeringdigest.journalApp.service.UserService;
import net.engineeringdigest.journalApp.Entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import static org.mockito.Mockito.when;

public class UserDetailsServiceImplTests {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    CustomUserDetailsServiceImplForSecurity userDetailsService;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void loadUserByUserNameTest(){

        when(userRepository.findByUserName(ArgumentMatchers.anyString())).thenReturn(new User.Builder().withUserName("Shyam").withPassword("Shyama").build());
          UserDetails user = userDetailsService.loadUserByUsername("Shyam");
    }

}
