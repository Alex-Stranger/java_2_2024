package lv.javaguru.travel.insurance.core.api.dto;

import lombok.*;

import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PersonDTO {

    @Size(max = 200, message = "First name must not exceed 200 characters!")
    private String personFirstName;
    @Size(max = 200, message = "Last name must not exceed 200 characters!")
    private String personLastName;

    private String personalCode;

    private Date personBirthDate;

    private String medicalRiskLimitLevel;

    private BigDecimal travelCost;

    private List<RiskDTO> risks;
}
