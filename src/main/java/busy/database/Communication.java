package busy.database;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "COMMUNICATION")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Communication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private Station firstStation;
    @ManyToOne
    private Station secondStation;

    private double distance;

    private double time;

}
