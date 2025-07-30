package lv.javaguru.travel.insurance.core.domain.cancellation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "travel_cancellation_country_safety_rating_coefficient")
@Entity
public class TCCountrySafetyRatingCoefficient {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "country_ic", nullable = false)
    String countryIc;

    @Column(name = "coefficient", precision = 10, scale = 2, nullable = false)
    BigDecimal coefficient;
}
