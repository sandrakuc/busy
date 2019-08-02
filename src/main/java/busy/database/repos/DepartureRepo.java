package busy.database.repos;

import busy.database.Departure;
import org.springframework.data.repository.CrudRepository;

public interface DepartureRepo extends CrudRepository<Departure, Long> {
}
