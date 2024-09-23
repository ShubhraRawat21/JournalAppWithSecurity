package net.engineeringdigest.journalApp.controller;
        import net.engineeringdigest.journalApp.Entity.JournalEntry;
        import net.engineeringdigest.journalApp.Entity.JournalEntryV2;
        import net.engineeringdigest.journalApp.service.JournalEntryService;
        import org.bson.types.ObjectId;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.http.HttpStatus;
        import org.springframework.http.ResponseEntity;
        import org.springframework.web.bind.annotation.*;

        import java.time.LocalDateTime;
        import java.util.List;
        import java.util.Optional;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {

    @Autowired
    JournalEntryService journalEntryService;
    @GetMapping
    public ResponseEntity<?> getAll() {
    List<JournalEntryV2> all = journalEntryService.getAll();
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


    @PostMapping
    public ResponseEntity<JournalEntryV2> createEntry(@RequestBody JournalEntryV2 je) {
      try {
          journalEntryService.saveEntry(je);
          return new ResponseEntity<JournalEntryV2>(HttpStatus.CREATED);
      }
      catch(Exception e){
          return new ResponseEntity<JournalEntryV2>(HttpStatus.NOT_ACCEPTABLE);
      }
    }

    @GetMapping("id/{myId}")
    public ResponseEntity<JournalEntryV2> getJEId(@PathVariable ObjectId myId) {
       Optional<JournalEntryV2> journalEntryV2 = journalEntryService.findById(myId);
       if(journalEntryV2.isPresent()){
           return new ResponseEntity<JournalEntryV2>(journalEntryV2.get(), HttpStatus.OK);
       }
       else{
           return new ResponseEntity<JournalEntryV2>(HttpStatus.NOT_FOUND);
       }
    }

//    @DeleteMapping("id/{myid}")
//    public JournalEntryV2 deleteJE(@PathVariable ObjectId myid) {
//        Optional<JournalEntryV2> jev2 = journalEntryService.findById(myid);
//        journalEntryService.deleteByid(myid);
//        return jev2.orElse(null) ;
//    }

    @DeleteMapping("id/{myid}")
    public ResponseEntity<?> deleteJE(@PathVariable ObjectId myid) {
        journalEntryService.deleteByid(myid);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT) ;
    }

    @DeleteMapping
    public String deleteJE() {
        String s = journalEntryService.deleteAll();
        return s;
    }

//    @PutMapping("id/{myId}")
//    public JournalEntryV2 putJE(@PathVariable ObjectId myId, @RequestBody JournalEntry newEntry) {
//       JournalEntryV2 old = journalEntryService.findById(myId).orElse(null);
//       if(old != null){
//       old.setTitle(newEntry.getTitle()!= null && !newEntry.getTitle().isEmpty()?newEntry.getTitle(): old.getTitle());
//       old.setContent(newEntry.getContent()!= null && !newEntry.getContent().isEmpty()?newEntry.getContent(): old.getContent());
//       }
//       journalEntryService.saveEntry(old);
//        return old;
//    }

    @PutMapping("id/{myId}")
    public ResponseEntity<?> putJE(@PathVariable ObjectId myId, @RequestBody JournalEntry newEntry) {
        JournalEntryV2 old = journalEntryService.findById(myId).orElse(null);
        if(old != null){
            old.setTitle(newEntry.getTitle()!= null && !newEntry.getTitle().isEmpty()?newEntry.getTitle(): old.getTitle());
            old.setContent(newEntry.getContent()!= null && !newEntry.getContent().isEmpty()?newEntry.getContent(): old.getContent());
            journalEntryService.saveEntry(old);
            return new ResponseEntity<JournalEntryV2>(old, HttpStatus.OK);
        }else {
            return new ResponseEntity<JournalEntryV2>(HttpStatus.NOT_FOUND);
        }
            }
}
