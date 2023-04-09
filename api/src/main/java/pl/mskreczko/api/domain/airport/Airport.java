package pl.mskreczko.api.domain.airport;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "airports")
public class Airport implements Serializable {

    @Id
    private String icao;
    private String name;
    private String country;

    @Override
    public String toString() {
        return "Airport{" +
                "icao='" + icao + '\'' +
                ", name='" + name + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}
