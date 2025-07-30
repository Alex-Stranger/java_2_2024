package lv.javaguru.travel.insurance.core.domain;

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
@Table(name = "travel_medical_country_default_day_rate")
@Entity
public class TMCountryDefaultDayRate {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "country_ic", nullable = false)
    String countryIc;

    @Column(name = "default_day_rate", precision = 10, scale = 2, nullable = false)
    private BigDecimal defaultDayRate;
}
