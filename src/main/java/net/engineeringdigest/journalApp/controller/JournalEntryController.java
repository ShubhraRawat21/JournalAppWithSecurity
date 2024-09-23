package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.Entity.JournalEntry;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/journal//")
public class JournalEntryController {

    Map<Long, JournalEntry> journalEntries = new HashMap<>();

    @GetMapping
    public List<JournalEntry> getAll(){
        
     return new ArrayList<>(journalEntries.values());
  }

  @PostMapping
    public boolean createEntry(@RequestBody JournalEntry je){
      journalEntries.put(je.getId(), je);
      return true;
  }

  @GetMapping("id/{myId}")
    public String getJEId( @PathVariable Long myId){
        JournalEntry je =  journalEntries.get(myId);
        return je.getTitle();
  }

  @DeleteMapping("id/{myid}")
    public JournalEntry deleteJE(@PathVariable long myid){
        return journalEntries.remove(myid);

  }

  @PutMapping("id/{myId}")
    public String putJE(@PathVariable Long myId, @RequestBody JournalEntry je){
        journalEntries.put(myId, je);
        return "updated";
  }
}
