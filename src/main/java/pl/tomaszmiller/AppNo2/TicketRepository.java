package pl.tomaszmiller.AppNo2;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.tomaszmiller.AppNo2.models.Ticket;

import java.util.List;
import java.util.Optional;

/**
 * Created by Peniakoff on 31.05.2017.
 */
@Repository
public interface TicketRepository extends CrudRepository<Ticket, Integer> {

    Optional<Ticket> findOne(int id); //overwrite the findOne method
    List<Ticket> findByAuthor(String author);
    List<Ticket> findByMessageLike(String prefix);

    @Query(value = "SELECT * FROM ticket WHERE author = ?1 LIMIT 1", nativeQuery = true) // ?1 odwołaj się do argumentu pierwszego, np. getTicket("Tomasz M.")
    Ticket getTicket(String person);

}
