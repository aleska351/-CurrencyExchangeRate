package com.goncharenko.currencyexchangerate.dto;

import com.goncharenko.currencyexchangerate.domain.Currency;
import lombok.*;
import org.springframework.stereotype.Component;

@Builder
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Component
public class CurrencyDto {
    private Long id;
    private String name;
    private String shortName;
    private Double purchase;
    private Double sale;

    public static Currency convertToDomain(CurrencyDto currencyDTO) {
        return new Currency(currencyDTO.getName(), currencyDTO.getShortName(), currencyDTO.getPurchase(), currencyDTO.getSale());
    }

    public static CurrencyDto convertToDTO(Currency createdCurrency) {
        return new CurrencyDto(createdCurrency.getId(), createdCurrency.getName(), createdCurrency.getShortName(), createdCurrency.getPurchase(), createdCurrency.getSale());
    }
}
