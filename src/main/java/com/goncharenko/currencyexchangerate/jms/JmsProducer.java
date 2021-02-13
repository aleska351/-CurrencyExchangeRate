package com.goncharenko.currencyexchangerate.jms;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.goncharenko.currencyexchangerate.dto.jms.JmsErrorDto;
import lombok.AllArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class JmsProducer {
    private final JmsTemplate jmsTemplate;
    private final ObjectMapper objectMapper;

    public void publishErrorMessage(String queue, JmsErrorDto jmsErrorDto) {
        try {
            jmsTemplate.convertAndSend(queue,
                    objectMapper.writeValueAsString(jmsErrorDto));
        } catch (JsonProcessingException e) {

        }
    }
}
