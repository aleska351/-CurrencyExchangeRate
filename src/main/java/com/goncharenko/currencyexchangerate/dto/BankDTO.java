package com.goncharenko.currencyexchangerate.dto;

import com.goncharenko.currencyexchangerate.domain.Bank;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class BankDTO {
    private Long id;
    private String name;
    private String phoneNumber;
    private Bank.Type bankType;
    private Boolean isOnlineAvailable;
    private Integer numberOfDepartments;
    private String address;
    private List<CurrencyDTO> currencyDTOList;


    public BankDTO(String name, String phoneNumber, Bank.Type bankType, Boolean isOnlineAvailable, Integer numberOfDepartments, String address, List<CurrencyDTO> currencyDTOList) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.bankType = bankType;
        this.isOnlineAvailable = isOnlineAvailable;
        this.numberOfDepartments = numberOfDepartments;
        this.address = address;
        this.currencyDTOList = currencyDTOList;
    }

    public BankDTO(Long id, String name, String phoneNumber, Bank.Type bankType, Boolean isOnlineAvailable, Integer numberOfDepartments, String address) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.bankType = bankType;
        this.isOnlineAvailable = isOnlineAvailable;
        this.numberOfDepartments = numberOfDepartments;
        this.address = address;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Bank.Type getBankType() {
        return bankType;
    }

    public void setBankType(Bank.Type bankType) {
        this.bankType = bankType;
    }

    public Boolean getOnlineAvailable() {
        return isOnlineAvailable;
    }

    public void setOnlineAvailable(Boolean onlineAvailable) {
        isOnlineAvailable = onlineAvailable;
    }

    public Integer getNumberOfDepartments() {
        return numberOfDepartments;
    }

    public void setNumberOfDepartments(Integer numberOfDepartments) {
        this.numberOfDepartments = numberOfDepartments;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<CurrencyDTO> getCurrencyDTOList() {
        return currencyDTOList;
    }

    public void setCurrencyDTOList(List<CurrencyDTO> currencyDTOList) {
        this.currencyDTOList = currencyDTOList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BankDTO bankDTO = (BankDTO) o;
        return Objects.equals(id, bankDTO.id) &&
                Objects.equals(name, bankDTO.name) &&
                Objects.equals(phoneNumber, bankDTO.phoneNumber) &&
                bankType == bankDTO.bankType &&
                Objects.equals(isOnlineAvailable, bankDTO.isOnlineAvailable) &&
                Objects.equals(numberOfDepartments, bankDTO.numberOfDepartments) &&
                Objects.equals(address, bankDTO.address) &&
                Objects.equals(currencyDTOList, bankDTO.currencyDTOList);
    }

    @Override
    public String toString() {
        return "BankDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", bankType=" + bankType +
                ", isOnlineAvailable=" + isOnlineAvailable +
                ", numberOfDepartments=" + numberOfDepartments +
                ", address='" + address + '\'' +
                ", currencyDTOList=" + currencyDTOList +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, phoneNumber, bankType, isOnlineAvailable, numberOfDepartments, address, currencyDTOList);
    }

    public BankDTO() {
    }

    public static Bank convertToDomain(BankDTO bankDTO) {
        return new Bank(bankDTO.getId(), bankDTO.getName(), bankDTO.getPhoneNumber(), bankDTO.getBankType(), bankDTO.getOnlineAvailable(), bankDTO.getNumberOfDepartments(), bankDTO.getAddress());
    }

    public static BankDTO convertToDTO(Bank createdBank) {
        return new BankDTO(createdBank.getId(), createdBank.getName(), createdBank.getPhoneNumber(), createdBank.getBankType(), createdBank.getOnlineAvailable(), createdBank.getNumberOfDepartments(), createdBank.getAddress());
    }
}
