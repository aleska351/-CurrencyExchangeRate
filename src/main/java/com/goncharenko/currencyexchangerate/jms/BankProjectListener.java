package com.goncharenko.currencyexchangerate.jms;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.goncharenko.currencyexchangerate.dto.BankDto;
import com.goncharenko.currencyexchangerate.dto.jms.JmsActionDto;
import com.goncharenko.currencyexchangerate.dto.jms.JmsActionEnum;
import com.goncharenko.currencyexchangerate.dto.jms.JmsErrorDto;
import com.goncharenko.currencyexchangerate.service.BankService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;


@Component
@Slf4j
@AllArgsConstructor
public class BankProjectListener {
    private final ObjectMapper objectMapper;
    private final BankService bankService;
    private final JmsProducer jmsProducer;

    @JmsListener(destination = "resource-management")
    public void onBankMessage(String message) {
        log.info("Message " + message);
        JmsActionDto jmsActionDto = null;
        try {
            jmsActionDto = objectMapper.readValue(message, JmsActionDto.class);
        } catch (JsonProcessingException e) {
            log.error("Cannot convert message to JSON", e.getMessage());
        }
        log.info("Message converted : {}", jmsActionDto);
        if (jmsActionDto.getAction().equals(JmsActionEnum.DELETE)) {
            if (jmsActionDto.getJmsBankDto() != null && jmsActionDto.getJmsBankDto().getId() != null) {
                bankService.delete(jmsActionDto.getJmsBankDto().getId());
            } else {
                log.error("Message in resource not defined");
                jmsProducer.publishErrorMessage("resource-management-error", new JmsErrorDto("Message cannot be converted", null));

            }
        } else if (jmsActionDto.getAction().equals(JmsActionEnum.UPDATE)) {
            if (jmsActionDto.getJmsBankDto() != null && jmsActionDto.getJmsBankDto().getId() != null) {
                bankService.update(jmsActionDto.getJmsBankDto().getId(), new BankDto(
                        jmsActionDto.getJmsBankDto().getId(),
                        jmsActionDto.getJmsBankDto().getName(),
                        jmsActionDto.getJmsBankDto().getPhoneNumber(),
                        jmsActionDto.getJmsBankDto().getBankType(),
                        jmsActionDto.getJmsBankDto().getIsOnlineAvailable(),
                        jmsActionDto.getJmsBankDto().getNumberOfDepartments(),
                        jmsActionDto.getJmsBankDto().getAddress()));
            } else {
                log.error("Message in resource not defined");
            }
        }
    }
}
