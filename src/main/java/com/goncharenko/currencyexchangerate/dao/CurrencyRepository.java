package com.goncharenko.currencyexchangerate.dao;

import com.goncharenko.currencyexchangerate.domain.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyRepository extends JpaRepository<Currency, Long> {
}
