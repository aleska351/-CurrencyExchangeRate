package com.goncharenko.currencyexchangerate.domain;

import java.util.Objects;


public class Bank {

    private Long id;
    private String name;
    private String phoneNumber;
    private Bank.Type bankType;
    private Boolean isOnlineAvailable;
    private Integer numberOfDepartments;
    private String address;

    public Bank() {

    }

    public Bank(String name, String phoneNumber, Bank.Type bankType, Boolean isOnlineAvailable, Integer numberOfDepartments, String address) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.bankType = bankType;
        this.isOnlineAvailable = isOnlineAvailable;
        this.numberOfDepartments = numberOfDepartments;
        this.address = address;
    }


    public Bank(Long id, String name, String phoneNumber, Bank.Type bankType, boolean isOnlineAvailable, Integer numberOfDepartments, String address) {
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


    @Override
    public String toString() {
        return "Bank{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", bankType=" + bankType.toString() +
                ", isOnlineAvailable=" + isOnlineAvailable +
                ", numberOfDepartments=" + numberOfDepartments +
                ", address='" + address + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bank bank = (Bank) o;
        return id.equals(bank.id) &&
                name.equals(bank.name) &&
                phoneNumber.equals(bank.phoneNumber) &&
                bankType == bank.bankType &&
                isOnlineAvailable.equals(bank.isOnlineAvailable) &&
                numberOfDepartments.equals(bank.numberOfDepartments) &&
                address.equals(bank.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, phoneNumber, bankType, isOnlineAvailable, numberOfDepartments, address);
    }

    public enum Type {

        GLOBAL(),
        LOCAL();

        Type() {

        }
    }
}
