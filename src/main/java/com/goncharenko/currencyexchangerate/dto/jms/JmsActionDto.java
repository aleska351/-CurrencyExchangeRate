package com.goncharenko.currencyexchangerate.dto.jms;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JmsActionDto {
    private JmsActionEnum action;
    private JmsBankDto jmsBankDto;
}
