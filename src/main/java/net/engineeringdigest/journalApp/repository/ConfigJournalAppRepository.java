package net.engineeringdigest.journalApp.repository;

import net.engineeringdigest.journalApp.Entity.ConfigJournalAppEntity;
import net.engineeringdigest.journalApp.Entity.JournalEntryV2;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ConfigJournalAppRepository extends MongoRepository <ConfigJournalAppEntity, String>{

}
