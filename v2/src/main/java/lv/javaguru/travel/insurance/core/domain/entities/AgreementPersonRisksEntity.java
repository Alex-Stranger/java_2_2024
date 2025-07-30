package lv.javaguru.travel.insurance.core.domain.entities;

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
@Table(name = "agreement_person_risks")
@Entity
public class AgreementPersonRisksEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "agreement_person_id", nullable = false)
    private AgreementPersonEntity agreementPerson;

    @Column(name = "risk_ic", nullable = false)
    private String riskIc;

    @Column(name = "premium", nullable = false)
    private BigDecimal premium;


}
