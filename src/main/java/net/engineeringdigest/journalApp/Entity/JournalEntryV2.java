package net.engineeringdigest.journalApp.Entity;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Date;

@Document(collection = "journalEntries")
@Data
@NoArgsConstructor
public class JournalEntryV2  {
    @Id
    private ObjectId id;
    @NonNull
    private String title;
    private String content;

}
