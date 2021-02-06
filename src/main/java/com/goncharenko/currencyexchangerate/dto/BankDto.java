package com.goncharenko.currencyexchangerate.dto;

import com.goncharenko.currencyexchangerate.domain.Bank;
import com.goncharenko.currencyexchangerate.domain.Type;
import lombok.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Builder
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Component
@ToString
public class BankDto {
    private Long id;
    private String name;
    private String phoneNumber;
    private Type bankType;
    private Boolean isOnlineAvailable;
    private Integer numberOfDepartments;
    private String address;
    @ToString.Exclude
    private List<CurrencyDto> currencyDtoList;

    public BankDto(Long id, String name, String phoneNumber, Type bankType, Boolean isOnlineAvailable, Integer numberOfDepartments, String address) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.bankType = bankType;
        this.isOnlineAvailable = isOnlineAvailable;
        this.numberOfDepartments = numberOfDepartments;
        this.address = address;
    }


    public static Bank convertToDomain(BankDto bankDTO) {
        return new Bank(bankDTO.getName(), bankDTO.getPhoneNumber(), bankDTO.getBankType(), bankDTO.getIsOnlineAvailable(), bankDTO.getNumberOfDepartments(), bankDTO.getAddress());
    }

    public static BankDto convertToDTO(Bank createdBank) {
        return new BankDto(createdBank.getId(), createdBank.getName(), createdBank.getPhoneNumber(), createdBank.getBankType(), createdBank.getIsOnlineAvailable(), createdBank.getNumberOfDepartments(), createdBank.getAddress());
    }
}