package surveys.model.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.*;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Survey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @OneToMany(mappedBy = "survey", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Column(nullable = false)
    @JsonManagedReference
    private List<Option> options;

    @OneToMany(mappedBy = "survey", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Column(nullable = false)
    private List<Vote> votes;

    @Column(nullable = false)
    private boolean active;

    @Column(nullable = false)
    private LocalDate created_at;

    public Survey(String title, List<String> options) {
        List<Option> _options = new ArrayList<>();
        for(String opt : options) {
            _options.add(new Option(opt, this));
        }
        this.options = _options;
        this.active = true;
    }
}

