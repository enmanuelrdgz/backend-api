package surveys.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "option_id")
    private Option option;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "survey_id")
    private Survey survey;

    private String ip_address;

    public Vote(Survey survey, Option option, String ip_address) {
        this.survey = survey;
        this.option = option;
        this.ip_address = ip_address;
    }
}