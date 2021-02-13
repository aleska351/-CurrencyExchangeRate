package com.goncharenko.currencyexchangerate.domain;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Builder
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "banks")
public class Bank extends AbstractAuditDomain {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "bank_type")
    @Enumerated(EnumType.STRING)
    private Type bankType;
    @Column(name = "is_online_available")
    private Boolean isOnlineAvailable;
    @Column(name = "number_of_departments")
    private Integer numberOfDepartments;
    @Column(name = "address")
    private String address;
    @OneToMany(mappedBy = "bank", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Currency> currencies;

    public Bank(String name, String phoneNumber, Type bankType, Boolean isOnlineAvailable, Integer numberOfDepartments, String address) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.bankType = bankType;
        this.isOnlineAvailable = isOnlineAvailable;
        this.numberOfDepartments = numberOfDepartments;
        this.address = address;
    }

}
