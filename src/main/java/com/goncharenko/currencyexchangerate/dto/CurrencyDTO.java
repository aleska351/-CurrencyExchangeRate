package com.goncharenko.currencyexchangerate.dto;

import com.goncharenko.currencyexchangerate.domain.Currency;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class CurrencyDTO {
    private Long id;
    private String name;
    private String shortName;
    private Double purchase;
    private Double sale;

    public CurrencyDTO() {
    }

    public CurrencyDTO(Long id, String name, String shortName, Double purchase, Double sale) {
        this.id = id;
        this.name = name;
        this.shortName = shortName;
        this.purchase = purchase;
        this.sale = sale;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CurrencyDTO that = (CurrencyDTO) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(shortName, that.shortName) &&
                Objects.equals(purchase, that.purchase) &&
                Objects.equals(sale, that.sale);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, shortName, purchase, sale);
    }

    @Override
    public String toString() {
        return "CurrencyDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", shortNameCurrency='" + shortName + '\'' +
                ", purchaseCurrency=" + purchase +
                ", saleCurrency=" + sale +
                '}';
    }

    public static Currency convertToDomain(CurrencyDTO currencyDTO) {
        return new Currency(currencyDTO.getName(), currencyDTO.getShortName(), currencyDTO.getPurchase(), currencyDTO.getSale());
    }

    public static CurrencyDTO convertToDTO(Currency createdCurrency) {
        return new CurrencyDTO(createdCurrency.getId(), createdCurrency.getName(), createdCurrency.getShortName(), createdCurrency.getPurchase(), createdCurrency.getSale());
    }
}
