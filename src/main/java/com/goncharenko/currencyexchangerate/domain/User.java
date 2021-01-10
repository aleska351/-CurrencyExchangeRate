package com.goncharenko.currencyexchangerate.domain;


import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Long id;
    private String name;
    private String password;

    public User(Long id) {
        this.id = id;
    }
}
