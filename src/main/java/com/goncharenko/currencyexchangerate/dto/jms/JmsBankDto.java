package com.goncharenko.currencyexchangerate.dto.jms;

import com.goncharenko.currencyexchangerate.domain.Type;
import com.goncharenko.currencyexchangerate.dto.CurrencyDto;
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
public class JmsBankDto {

        private Long id;
        private String name;
        private String phoneNumber;
        private Type bankType;
        private Boolean isOnlineAvailable;
        private Integer numberOfDepartments;
        private String address;
}
