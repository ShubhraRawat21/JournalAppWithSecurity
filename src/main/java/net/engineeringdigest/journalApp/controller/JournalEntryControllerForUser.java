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
        import org.springframework.web.bind.annotation.*;

        import java.time.LocalDateTime;
        import java.util.List;
        import java.util.Optional;

@RestController
@RequestMapping("/journalForUser")
public class JournalEntryControllerForUser {

    @Autowired
    JournalEntryServiceForUser journalEntryServiceForUser;

    @Autowired
    UserService userService;
    @GetMapping ("{userName}")
    public ResponseEntity<?> getAllJournalEntriesofUser(@PathVariable String userName) {
        User user = userService.findByUserName(userName);
        List<JournalEntryV2> all = user.getJournalEntryV2s();
        if(all != null && !all.isEmpty()){
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<JournalEntryV2>(HttpStatus.NOT_FOUND);
        }
    }

//    @PostMapping
//    public boolean createEntry(@RequestBody JournalEntryV2 je) {
//        je.setDate(LocalDateTime.now());
//       journalEntryService.saveEntry(je);
//        return true;
//    }


    @PostMapping("{username}")
    public ResponseEntity<JournalEntryV2> createEntry(@RequestBody JournalEntryV2 je, @PathVariable String username) {
        try {
            journalEntryServiceForUser.saveEntry(je, username);
            return new ResponseEntity<JournalEntryV2>(HttpStatus.CREATED);
        }
        catch(Exception e){
            return new ResponseEntity<JournalEntryV2>(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @GetMapping("id/{myId}")
    public ResponseEntity<JournalEntryV2> getJEId(@PathVariable ObjectId myId) {
        Optional<JournalEntryV2> journalEntryV2 = journalEntryServiceForUser.findById(myId);
        if(journalEntryV2.isPresent()){
            return new ResponseEntity<JournalEntryV2>(journalEntryV2.get(), HttpStatus.OK);
        }
        else{
            return new ResponseEntity<JournalEntryV2>(HttpStatus.NOT_FOUND);
        }
    }

//    @DeleteMapping("id/{myid}")
//    public JournalEntryV2 deleteJE(@PathVariable ObjectId myid) {
//        Optional<JournalEntryV2> jev2 = journalEntryServiceForUser.findById(myid);
//        journalEntryServiceForUser.deleteByid(myid);
//        return jev2.orElse(null) ;
//    }

    @DeleteMapping("id/{userName}/{myid}")
    public ResponseEntity<?> deleteJE(@PathVariable ObjectId myid, @PathVariable String userName) {
        journalEntryServiceForUser.deleteByid(myid, userName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT) ;
    }

    @DeleteMapping
    public String deleteJE() {
        String s = journalEntryServiceForUser.deleteAll();
        return s;
    }

//    @PutMapping("id/{myId}")
//    public JournalEntryV2 putJE(@PathVariable ObjectId myId, @RequestBody JournalEntry newEntry) {
//       JournalEntryV2 old = journalEntryServiceForUser.findById(myId).orElse(null);
//       if(old != null){
//       old.setTitle(newEntry.getTitle()!= null && !newEntry.getTitle().isEmpty()?newEntry.getTitle(): old.getTitle());
//       old.setContent(newEntry.getContent()!= null && !newEntry.getContent().isEmpty()?newEntry.getContent(): old.getContent());
//       }
//       journalEntryServiceForUser.saveEntry(old);
//        return old;
//    }

    @PutMapping("id/{myId}")
    public ResponseEntity<?> putJE(@PathVariable ObjectId myId, @RequestBody JournalEntry newEntry, @PathVariable String userName) {
        JournalEntryV2 old = journalEntryServiceForUser.findById(myId).orElse(null);
        if (old != null) {
            old.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().isEmpty() ? newEntry.getTitle() : old.getTitle());
            old.setContent(newEntry.getContent() != null && !newEntry.getContent().isEmpty() ? newEntry.getContent() : old.getContent());
             journalEntryServiceForUser.updateEntry(old);
            return new ResponseEntity<JournalEntryV2>(old, HttpStatus.OK);
        } else {
            return new ResponseEntity<JournalEntryV2>(HttpStatus.NOT_FOUND);
        }
    }
}
