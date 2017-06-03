package pl.tomaszmiller.AppNo2;

import org.springframework.data.repository.CrudRepository;
import pl.tomaszmiller.AppNo2.models.User;

/**
 * Created by Peniakoff on 03.06.2017.
 */
public interface UserRepository extends CrudRepository<User, Integer> {



}
