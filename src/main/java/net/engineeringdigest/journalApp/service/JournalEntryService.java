package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.Entity.JournalEntry;
import net.engineeringdigest.journalApp.Entity.JournalEntryV2;
import net.engineeringdigest.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JournalEntryService {

@Autowired
    JournalEntryRepository jr;

public void saveEntry(JournalEntryV2 je){
    jr.save(je);
}

public List<JournalEntryV2> getAll(){
    return jr.findAll();
}

public Optional<JournalEntryV2> findById(ObjectId id){
    return jr.findById(id);
}

public String deleteAll(){
    jr.deleteAll();
    return "All entries deleted";}


public String deleteByid(ObjectId id){
     jr.deleteById(id);
    return "Delete";
}
}