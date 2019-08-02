package busy.database.repos;

import busy.database.Station;
import org.springframework.data.repository.CrudRepository;

public interface StationRepo extends CrudRepository<Station, Long> {
}
