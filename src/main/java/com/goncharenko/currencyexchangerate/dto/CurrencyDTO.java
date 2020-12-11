package com.goncharenko.currencyexchangerate.dto;

import com.goncharenko.currencyexchangerate.domain.Currency;

import java.util.Objects;

public class CurrencyDTO {
    private Long id;
    private String name;
    private String shortNameCurrency;
    private Double purchaseCurrency;
    private Double saleCurrency;

    public CurrencyDTO() {
    }

    public CurrencyDTO(String name, String shortNameCurrency, Double purchaseCurrency, Double saleCurrency) {
        this.name = name;
        this.shortNameCurrency = shortNameCurrency;
        this.purchaseCurrency = purchaseCurrency;
        this.saleCurrency = saleCurrency;
    }

    public CurrencyDTO(Long id, String name, String shortNameCurrency, Double purchaseCurrency, Double saleCurrency) {
        this.id = id;
        this.name = name;
        this.shortNameCurrency = shortNameCurrency;
        this.purchaseCurrency = purchaseCurrency;
        this.saleCurrency = saleCurrency;
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

    public String getShortNameCurrency() {
        return shortNameCurrency;
    }

    public void setShortNameCurrency(String shortNameCurrency) {
        this.shortNameCurrency = shortNameCurrency;
    }

    public Double getPurchaseCurrency() {
        return purchaseCurrency;
    }

    public void setPurchaseCurrency(Double purchaseCurrency) {
        this.purchaseCurrency = purchaseCurrency;
    }

    public Double getSaleCurrency() {
        return saleCurrency;
    }

    public void setSaleCurrency(Double saleCurrency) {
        this.saleCurrency = saleCurrency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CurrencyDTO that = (CurrencyDTO) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(shortNameCurrency, that.shortNameCurrency) &&
                Objects.equals(purchaseCurrency, that.purchaseCurrency) &&
                Objects.equals(saleCurrency, that.saleCurrency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, shortNameCurrency, purchaseCurrency, saleCurrency);
    }

    @Override
    public String toString() {
        return "CurrencyDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", shortNameCurrency='" + shortNameCurrency + '\'' +
                ", purchaseCurrency=" + purchaseCurrency +
                ", saleCurrency=" + saleCurrency +
                '}';
    }

    public static Currency convertToDomain(CurrencyDTO currencyDTO) {
        return new Currency(currencyDTO.getName(), currencyDTO.getShortNameCurrency(), currencyDTO.getPurchaseCurrency(), currencyDTO.getSaleCurrency());
    }

    public static CurrencyDTO convertToDTO(Currency createdCurrency) {
        return new CurrencyDTO(createdCurrency.getId(), createdCurrency.getName(), createdCurrency.getShortName(), createdCurrency.getPurchase(), createdCurrency.getSale());
    }
}
