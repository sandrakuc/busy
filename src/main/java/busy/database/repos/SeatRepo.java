package busy.database.repos;

import busy.database.Seat;
import org.springframework.data.repository.CrudRepository;

public interface SeatRepo extends CrudRepository<Seat, Long> {
}
