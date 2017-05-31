package pl.tomaszmiller.AppNo2;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.tomaszmiller.AppNo2.models.Ticket;

/**
 * Created by Peniakoff on 31.05.2017.
 */
@Repository
public interface TicketRepository extends CrudRepository<Ticket, Integer> {



}
