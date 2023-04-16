package pl.mskreczko.api.domain.user;

import jakarta.persistence.*;
import lombok.*;
import pl.mskreczko.api.domain.ticket.Ticket;
import pl.mskreczko.api.domain.user.role.Role;

import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {

    @Id
    private UUID id;
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private boolean enabled = false;

    @OneToMany(mappedBy = "user")
    private List<Ticket> tickets = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();
}
