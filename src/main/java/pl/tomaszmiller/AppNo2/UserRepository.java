package pl.tomaszmiller.AppNo2;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import pl.tomaszmiller.AppNo2.models.User;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Created by Peniakoff on 03.06.2017.
 */
public interface UserRepository extends CrudRepository<User, Integer> {

    List<User> findByRole(String role);
    List<User> findByDatetimeBetween(Date date1, Date date2);
    List<User> findByUsernameContainingAndIdGreaterThan(String reg, int id);
    Page<User> findAll(Pageable pageable);
    Optional<User> findByUsername(String name);

}
