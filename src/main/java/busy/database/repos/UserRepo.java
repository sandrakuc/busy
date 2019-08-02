package busy.database.repos;

import busy.database.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepo extends CrudRepository<User, Long> {
    User getByLogin(String login);
    User getByEmail(String email);
}
