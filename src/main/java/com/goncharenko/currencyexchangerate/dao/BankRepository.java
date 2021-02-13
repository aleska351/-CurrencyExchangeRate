package com.goncharenko.currencyexchangerate.dao;

import com.goncharenko.currencyexchangerate.domain.Bank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface BankRepository extends JpaRepository<Bank, Long> {

    List<Bank> findAllByNameContains(String partName);

    List<Bank> findAllByCreatedDateAfter(Instant date);


    List<Bank> findAllByNameOrAddress(String name, String address);
}
