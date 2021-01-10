package com.goncharenko.currencyexchangerate.domain;

import lombok.*;

@Builder
@Setter
@Getter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor

public class Currency {

    private Long id;
    private String name;
    private String shortName;
    private Double purchase;
    private Double sale;
    private Long bankId;

    public Currency( String name, String shortName, Double purchase, Double sale) {
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
                ", bankId=" + bankId +
                '}';
    }
}

