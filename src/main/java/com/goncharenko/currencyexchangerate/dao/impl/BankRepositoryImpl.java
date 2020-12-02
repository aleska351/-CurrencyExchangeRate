package com.goncharenko.currencyexchangerate.dao.impl;

import com.goncharenko.currencyexchangerate.dao.BankRepository;
import com.goncharenko.currencyexchangerate.dao.CurrencyRepository;
import com.goncharenko.currencyexchangerate.domain.Bank;
import com.goncharenko.currencyexchangerate.mapper.BankRowMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;


@Component
public class BankRepositoryImpl implements BankRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(BankRepositoryImpl.class);
    private final JdbcTemplate jdbcTemplate;
    private final CurrencyRepository currencyRepository;
    private final BankRowMapper bankMapper;

    @Autowired
    public BankRepositoryImpl(JdbcTemplate jdbcTemplate, CurrencyRepository currencyRepository, BankRowMapper bankMapper) {
        this.currencyRepository = currencyRepository;
        this.jdbcTemplate = jdbcTemplate;
        this.bankMapper = bankMapper;
        LOGGER.info("================BankRepository constructor is called===========");
    }

    /**
     * This method retrieves bank by id
     *
     * @param id bank id
     * @return current bank or Optional.empty() if bank with given id not found
     */
    @Override
    public Optional<Bank> retrieveById(Long id) {
        LOGGER.info("retrieve bank with id {} ", id);
        try {
            return Optional.of(
                    jdbcTemplate.queryForObject
                            ("SELECT * FROM banks where id = ?", bankMapper, id));
        } catch (EmptyResultDataAccessException e) {
            LOGGER.debug("Empty result, no bank found with given ID ");
            return Optional.empty();
        }
    }

    /**
     * This method retrieves all banks from table banks.
     *
     * @return all banks from table banks or Optional.empty() if table is empty.
     */
    @Override
    public Optional<List<Bank>> retrieveAll() {
        LOGGER.info("retrieve all banks");
        try {
            return Optional.of(jdbcTemplate.query("SELECT * FROM banks", bankMapper));
        } catch (DataAccessException e) {
            LOGGER.debug("Empty result, banks table is empty ");
            return Optional.empty();
        }
    }

    /**
     * This method creates currency in table
     *
     * @param bank new Bank object.
     * @return created bank or Optional.empty() if bank was not created.
     */
    @Override
    public Optional<Bank> create(Bank bank) {
        LOGGER.debug("creating new Bank with properties {} ", bank);
        LOGGER.info("creating new bank");
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement statement = con.prepareStatement("INSERT INTO" +
                    " banks(name, phone_number, bank_type, is_online_available, number_of_departments, address)" +
                    " VALUES(?, ?, ?, ?, ?, ?)", new String[]{"id"});
            statement.setString(1, bank.getName());
            statement.setString(2, bank.getPhoneNumber());
            statement.setString(3, String.valueOf(bank.getBankType()));
            statement.setBoolean(4, bank.getOnlineAvailable());
            statement.setInt(5, bank.getNumberOfDepartments());
            statement.setString(6, bank.getAddress());
            return statement;
        }, keyHolder);
        long bankId = keyHolder.getKey().longValue();
        return retrieveById(bankId);
    }

    /**
     * This method updates currency with given id in table
     *
     * @param id   currency ID which need to change
     * @param bank new Bank object with changed params.
     * @return updated bank or Optional.empty() if currency was not updated.
     */
    @Override
    public Optional<Bank> update(Long id, Bank bank) {
        jdbcTemplate.update("UPDATE banks" +
                        " SET name = ?, phone_number = ?, bank_type = ?,is_online_available = ?, number_of_departments =? , address =?  WHERE id = ?",
                bank.getName(), bank.getPhoneNumber(), String.valueOf(bank.getBankType()), bank.getOnlineAvailable(),
                bank.getNumberOfDepartments(), bank.getAddress(), id);
        return retrieveById(id);
    }

    /**
     * This method deletes bank with given id in table
     *
     * @param id bank ID which need to delete
     */
    @Override
    public void delete(Long id) {

        jdbcTemplate.update("DELETE from banks  WHERE id = ?", id);
        // delete all bank's currencies after bank deletion
        currencyRepository.deleteAllByBankId(id);

    }

}