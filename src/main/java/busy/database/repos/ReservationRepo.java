package busy.database.repos;

import busy.database.Reservation;
import org.springframework.data.repository.CrudRepository;

public interface ReservationRepo extends CrudRepository<Reservation,Long> {
}
