package surveys.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VoteDTO {
    private Long id;
    private Long survey_id;
    private Long option_id;
    private String ipAddress;
}
