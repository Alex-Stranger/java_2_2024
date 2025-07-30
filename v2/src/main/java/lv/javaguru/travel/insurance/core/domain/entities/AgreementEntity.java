package lv.javaguru.travel.insurance.core.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "agreements")
@Entity
public class AgreementEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_from", nullable = false)
    private Date agreementDateFrom;

    @Column(name = "date_to", nullable = false)
    private Date agreementDateTo;

    @Column(name = "country", nullable = false)
    private String country;

    @Column(name = "premium", nullable = false)
    private BigDecimal agreementPremium;

    @Column(name = "uuid", nullable = false)
    private String uuid;
}
