package com.goncharenko.currencyexchangerate.dao;

import com.goncharenko.currencyexchangerate.domain.Bank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankRepository extends JpaRepository<Bank, Long> {

}
