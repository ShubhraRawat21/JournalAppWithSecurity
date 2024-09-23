package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.Entity.JournalEntry;
import net.engineeringdigest.journalApp.Entity.JournalEntryV2;
import net.engineeringdigest.journalApp.Entity.User;
import net.engineeringdigest.journalApp.service.JournalEntryServiceForUser;

import net.engineeringdigest.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/journalForUserSecured")
public class JornalUserControllerWithSecurity {

    @Autowired
    JournalEntryServiceForUser journalEntryServiceForUser;

    @Autowired
    UserService userService;

    @GetMapping
    public ResponseEntity<?> getAllJournalEntriesofUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userService.findByUserName(userName);
        List<JournalEntryV2> all = user.getJournalEntryV2s();
        if (all != null && !all.isEmpty()) {
            return new ResponseEntity<>(all, HttpStatus.OK);
        } else {
            return new ResponseEntity<JournalEntryV2>(HttpStatus.NOT_FOUND);
        }
    }


    @PostMapping
    public ResponseEntity<JournalEntryV2> createEntry(@RequestBody JournalEntryV2 je) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();
            journalEntryServiceForUser.saveEntry(je, userName);
            return new ResponseEntity<JournalEntryV2>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<JournalEntryV2>(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @GetMapping("id/{myId}")
    public ResponseEntity<JournalEntryV2> getJEId(@PathVariable ObjectId myId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userService.findByUserName(userName);
        List<JournalEntryV2> jeList = user.getJournalEntryV2s().stream().filter(x -> x.getId().equals(myId)).collect(Collectors.toList());
        if (!jeList.isEmpty()) {
            Optional<JournalEntryV2> journalEntryV2 = journalEntryServiceForUser.findById(myId);
            if (journalEntryV2.isPresent()) {
                return new ResponseEntity<JournalEntryV2>(journalEntryV2.get(), HttpStatus.OK);
            }
        }
        return new ResponseEntity<JournalEntryV2>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("id/{myid}")
    public ResponseEntity<?> deleteJE(@PathVariable ObjectId myid) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        journalEntryServiceForUser.deleteByid(myid, userName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    public String deleteJE() {
        String s = journalEntryServiceForUser.deleteAll();
        return s;
    }


    @PutMapping("id/{myId}")
    public ResponseEntity<?> putJE(@PathVariable ObjectId myId, @RequestBody JournalEntry newEntry) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userService.findByUserName(userName);
        List<JournalEntryV2> jeList = user.getJournalEntryV2s().stream().filter(x -> x.getId().equals(myId)).collect(Collectors.toList());
        if (!jeList.isEmpty()) {
            Optional<JournalEntryV2> journalEntryV2 = journalEntryServiceForUser.findById(myId);
            if (journalEntryV2.isPresent()) {
                JournalEntryV2 old = journalEntryV2.get();
                if (old != null) {
                    old.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().isEmpty() ? newEntry.getTitle() : old.getTitle());
                    old.setContent(newEntry.getContent() != null && !newEntry.getContent().isEmpty() ? newEntry.getContent() : old.getContent());
                    journalEntryServiceForUser.updateEntry(old);
                    return new ResponseEntity<JournalEntryV2>(old, HttpStatus.OK);
                }
            }
        }
        return new ResponseEntity<JournalEntryV2>(HttpStatus.NOT_FOUND);
    }
}
