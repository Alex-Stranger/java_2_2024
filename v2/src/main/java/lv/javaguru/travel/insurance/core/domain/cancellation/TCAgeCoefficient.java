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
@Table(name = "travel_cancellation_age_coefficient")
@Entity
public class TCAgeCoefficient {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "age_from", nullable = false)
    Integer ageFrom;

    @Column(name = "age_to", nullable = false)
    Integer ageTo;

    @Column(name = "coefficient", precision = 10, scale = 2, nullable = false)
    BigDecimal coefficient;
}
