package com.goncharenko.currencyexchangerate.controllers;

import com.goncharenko.currencyexchangerate.dto.CurrencyDTO;
import com.goncharenko.currencyexchangerate.service.CurrencyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/banks/{bankId}/currencies")
public class CurrencyController {
    private final CurrencyService currencyService;

    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @PostMapping
    public ResponseEntity<CurrencyDTO> create(@PathVariable Long bankId, @RequestBody CurrencyDTO currencyDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(currencyService.create(bankId, currencyDTO));
    }

    @GetMapping
    public ResponseEntity<List<CurrencyDTO>> getCurrenciesByBankId(@PathVariable Long bankId) {
        return ResponseEntity.status(HttpStatus.OK).body(currencyService.getAllCurrenciesByBankId(bankId));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<CurrencyDTO> getCurrency(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(currencyService.getById(id));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<CurrencyDTO> update(@PathVariable Long id,
                                              @RequestBody CurrencyDTO currencyDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(currencyService.update(id, currencyDTO));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        currencyService.delete(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT).build();
    }
}