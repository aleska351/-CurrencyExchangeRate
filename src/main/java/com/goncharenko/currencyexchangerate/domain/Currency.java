package com.goncharenko.currencyexchangerate.domain;

import lombok.*;
import org.codehaus.jackson.annotate.JsonBackReference;

import javax.persistence.*;

@Builder
@Setter
@Getter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "currencies")
public class Currency extends AbstractAuditDomain {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String shortName;
    private Double purchase;
    private Double sale;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "bank_id")
    @ToString.Exclude
    private Bank bank;

    public Currency(String name, String shortName, Double purchase, Double sale) {
        this.id = id;
        this.name = name;
        this.shortName = shortName;
        this.purchase = purchase;
        this.sale = sale;
    }
}

