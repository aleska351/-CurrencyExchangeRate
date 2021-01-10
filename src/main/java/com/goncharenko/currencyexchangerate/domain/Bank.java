package com.goncharenko.currencyexchangerate.domain;

import lombok.*;

@Builder
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor

public class Bank {
    private Long id;
    private String name;
    private String phoneNumber;
    private Type bankType;
    private Boolean isOnlineAvailable;
    private Integer numberOfDepartments;
    private String address;

    public Bank(String name, String phoneNumber, Type bankType, Boolean isOnlineAvailable, Integer numberOfDepartments, String address) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.bankType = bankType;
        this.isOnlineAvailable = isOnlineAvailable;
        this.numberOfDepartments = numberOfDepartments;
        this.address = address;
    }

    @Override
    public String toString() {
        return "Bank{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", bankType=" + bankType +
                ", isOnlineAvailable=" + isOnlineAvailable +
                ", numberOfDepartments=" + numberOfDepartments +
                ", address='" + address + '\'' +
                '}';
    }
}
