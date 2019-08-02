package busy.database;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "RESERVATION")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private int numberOfSeats;

    @OneToOne
    private Communication communication;

    @OneToOne
    private Departure departure;

    @ManyToOne
    private User user;

    private String date;

    private double price;


}
