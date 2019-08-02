package busy.database;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="USER")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String login;

    private String password;

    private String email;

    private String phoneNumber;

    private String name;

    private String surname;

    @Enumerated(EnumType.STRING)
    private Privileges privileges;

    @OneToMany(mappedBy = "user")
    private List<Reservation> reservations;

    public boolean isNotBlank(){
        if(name != null && surname != null && email != null && login != null && password != null){
            return true;
        }
        return false;
    }

}
