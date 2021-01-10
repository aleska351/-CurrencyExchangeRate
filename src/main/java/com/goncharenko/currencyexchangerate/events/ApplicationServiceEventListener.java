package com.goncharenko.currencyexchangerate.events;

import com.goncharenko.currencyexchangerate.service.BankService;
import com.goncharenko.currencyexchangerate.service.CurrencyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class ApplicationServiceEventListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationReadyEventListener.class);
    private final BankService bankService;
    private final CurrencyService currencyService;


    public ApplicationServiceEventListener(BankService bankService, CurrencyService currencyService) {
        this.bankService = bankService;
        this.currencyService = currencyService;
    }


    @EventListener(ApplicationReadyEventListener.class)
    public void checkService() {
    /*    List<CurrencyDTO> currencies = new ArrayList<>();
        currencies.add(new CurrencyDTO("Zlota", "Zl", 6.25, 6.33));
        currencies.add(new CurrencyDTO("Crona", "Cz", 6.25, 6.33));
        BankDTO createdBankDTO = bankService.create(new BankDTO("NewBankDTO", "+3456536890", Bank.Type.GLOBAL, true, 67, "Dnepr", currencies));
        LOGGER.info("Create BankDTO " + createdBankDTO);
        LOGGER.info("Retrieve BankDTO by id " + bankService.retrieveById(createdBankDTO.getId()));
        LOGGER.info("Retrieve all BankDTO " + bankService.retrieveAll());
        bankService.delete(createdBankDTO.getId());
        LOGGER.info("Delete BankDTO by id " + createdBankDTO.getId());


        CurrencyDTO createdCurrencyDTO = currencyService.create(2L, new CurrencyDTO("Buka", "Bk", 43.8, 45.5));
        LOGGER.info("Create CurrencyDTO " + createdCurrencyDTO);
        LOGGER.info("Retrieve CurrencyDTO by id " + currencyService.retrieveById(createdCurrencyDTO.getId()));
        LOGGER.info("Retrieve all BankDTO " + currencyService.retrieveAll());
        LOGGER.info("Retrieve all CurrencyDTO by Bank id" + currencyService.retrieveAllCurrenciesByBankId(2L));
        currencyService.delete(createdCurrencyDTO.getId());
        LOGGER.info("DeleteCurrencyDTO by id " + createdCurrencyDTO.getId());


    }*/
    }
}