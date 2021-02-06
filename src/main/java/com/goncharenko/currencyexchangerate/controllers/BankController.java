package com.goncharenko.currencyexchangerate.controllers;

import com.goncharenko.currencyexchangerate.dto.BankDTO;
import com.goncharenko.currencyexchangerate.dto.CurrencyDTO;
import com.goncharenko.currencyexchangerate.service.BankService;
import com.goncharenko.currencyexchangerate.service.CurrencyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/banks")
public class BankController {
    private final BankService bankService;
    private final CurrencyService currencyService;


    public BankController(BankService bankService, CurrencyService currencyService) {
        this.bankService = bankService;
        this.currencyService = currencyService;
    }

    @PostMapping
    public ResponseEntity<BankDTO> create(@RequestBody BankDTO bankDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bankService.create(bankDTO));
    }

    @GetMapping
    public ResponseEntity<List<BankDTO>> getBanks(@RequestParam(required = false) String search,
                                                  @RequestParam(required = false) String sortField) {
        return ResponseEntity.status(HttpStatus.OK).body(bankService.getAll(search, sortField));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<BankDTO> getBank(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(bankService.getById(id));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<BankDTO> update(@PathVariable Long id,
                                          @RequestBody BankDTO bankDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bankService.update(id, bankDTO));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        bankService.delete(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT).build();
    }

}

