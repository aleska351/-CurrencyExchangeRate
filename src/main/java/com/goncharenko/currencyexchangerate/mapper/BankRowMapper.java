package com.goncharenko.currencyexchangerate.mapper;

import com.goncharenko.currencyexchangerate.domain.Bank;
import com.goncharenko.currencyexchangerate.domain.Type;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class BankRowMapper implements RowMapper<Bank> {

    @Override
    public Bank mapRow(ResultSet rs, int rowNum) throws SQLException {
        Bank bank = new Bank();
        bank.setId(rs.getLong("id"));
        bank.setName(rs.getString("name"));
        bank.setPhoneNumber(rs.getString("phone_number"));
        bank.setBankType(Type.valueOf(rs.getString("bank_type")));
        bank.setIsOnlineAvailable(rs.getBoolean("is_online_available"));
        bank.setNumberOfDepartments(rs.getInt("number_of_departments"));
        bank.setAddress(rs.getString("address"));
        return bank;
    }
}
