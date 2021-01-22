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
public class Currency {
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
    private Bank bank;

    public Currency(String name, String shortName, Double purchase, Double sale) {
        this.id = id;
        this.name = name;
        this.shortName = shortName;
        this.purchase = purchase;
        this.sale = sale;
    }

    @Override
    public String toString() {
        return "Currency{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", shortName='" + shortName + '\'' +
                ", purchase=" + purchase +
                ", sale=" + sale +
                '}';
    }
}

