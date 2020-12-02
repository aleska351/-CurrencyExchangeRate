package com.goncharenko.currencyexchangerate.domain;

import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class Currency {
    private Long id;
    private String name;
    private String shortName;
    private Double purchase;
    private Double sale;
    private Long bankId;

    public Currency() {
    }

    public Currency(String name, String shortName, Double purchase, Double sale) {
        this.name = name;
        this.shortName = shortName;
        this.purchase = purchase;
        this.sale = sale;
    }
    public Currency(String name, String shortName, Double purchase, Double sale, Long bankId) {
        this.name = name;
        this.shortName = shortName;
        this.purchase = purchase;
        this.sale = sale;
        this.bankId=bankId;
    }
    public Currency(Long id, String name, String shortName, Double purchase, Double sale, Long bankId) {
        this.id = id;
        this.name = name;
        this.shortName = shortName;
        this.purchase = purchase;
        this.sale = sale;
        this.bankId = bankId;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public Double getPurchase() {
        return purchase;
    }

    public void setPurchase(Double purchase) {
        this.purchase = purchase;
    }

    public Double getSale() {
        return sale;
    }

    public void setSale(Double sale) {
        this.sale = sale;
    }

    public Long getBankId() {
        return bankId;
    }

    public void setBankId(Long bankId) {
        this.bankId = bankId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Currency currency = (Currency) o;
        return Objects.equals(id, currency.id) &&
                Objects.equals(name, currency.name) &&
                Objects.equals(shortName, currency.shortName) &&
                Objects.equals(purchase, currency.purchase) &&
                Objects.equals(sale, currency.sale) &&
                Objects.equals(bankId, currency.bankId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, shortName, purchase, sale, bankId);
    }

    @Override
    public String toString() {
        return "Currency{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", shortNameCurrency='" + shortName + '\'' +
                ", purchaseCurrency=" + purchase +
                ", saleCurrency=" + sale +
                ", bankId=" + bankId +
                '}';
    }
}

