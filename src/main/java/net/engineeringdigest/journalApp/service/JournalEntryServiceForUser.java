package net.engineeringdigest.journalApp.service;

        import net.engineeringdigest.journalApp.Entity.JournalEntryV2;
        import net.engineeringdigest.journalApp.Entity.User;
        import net.engineeringdigest.journalApp.repository.JournalEntryRepository;
        import org.bson.types.ObjectId;
        import org.slf4j.Logger;
        import org.slf4j.LoggerFactory;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.stereotype.Service;
        import org.springframework.transaction.annotation.Transactional;

        import java.util.List;
        import java.util.Optional;

@Service
public class JournalEntryServiceForUser {

    @Autowired
    JournalEntryRepository jr;

    @Autowired
    UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(JournalEntryServiceForUser.class);
    @Transactional
    public void saveEntry(JournalEntryV2 je, String userName){
        try {
            User user = userService.findByUserName(userName);
            JournalEntryV2 saved = jr.save(je);
            user.getJournalEntryV2s().add(saved);
            userService.saveEntry(user);
        }catch (Exception e){
            logger.info("hahaha");
            System.out.println("Exception");
        }
    }

    public void updateEntry(JournalEntryV2 je){
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
        return "All entries deleted";
    }


    @Transactional
    public String deleteByid(ObjectId id, String userName){
       try{ User user = userService.findByUserName(userName);
        boolean removed = user.getJournalEntryV2s().removeIf(x -> x.getId().equals(id));
        if(removed) {
            userService.saveEntry(user);
            jr.deleteById(id);
            return "Deleted";
        }}catch(Exception e){
           System.out.println(e);
           throw new RuntimeException("Provided id Does not exist", e);
       }
        return "Provided id Does not exist";
    }
}