package com.goncharenko.currencyexchangerate.mapper;

import com.goncharenko.currencyexchangerate.domain.Currency;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class CurrencyRowMapper implements RowMapper<Currency> {

    @Override
    public Currency mapRow(ResultSet rs, int rowNum) throws SQLException {
        Currency currency = new Currency();
        currency.setId(rs.getLong("id"));
        currency.setName(rs.getString("name"));
        currency.setShortName(rs.getString("short_name"));
        currency.setPurchase(rs.getDouble("purchase"));
        currency.setSale(rs.getDouble("sale"));
        currency.setBankId(rs.getLong("bank_id"));
        return currency;
    }
}
