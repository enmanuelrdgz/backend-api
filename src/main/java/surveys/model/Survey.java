package surveys.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import surveys.dto.OptionDTO;
import surveys.dto.SurveyDTO;

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

    @OneToMany(mappedBy = "survey", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Column(nullable = false)
    private List<Vote> votes;

    @Column(nullable = false)
    private boolean active;

    @Column(nullable = false)
    private LocalDate created_at;

    public Survey(SurveyDTO surveyDTO) {
        List<Option> optionList = new ArrayList<>();
        for(OptionDTO optionDTO : surveyDTO.getOptions()) {
            Option option = new Option();
            option.setDescription(optionDTO.getDescription());
            option.setSurvey(this);
            optionList.add(option);
        }
        this.options = optionList;
        this.active = true;
    }
}

