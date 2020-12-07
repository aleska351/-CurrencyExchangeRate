package com.goncharenko.currencyexchangerate.dao.impl;

import com.goncharenko.currencyexchangerate.dao.CurrencyRepository;
import com.goncharenko.currencyexchangerate.domain.Currency;
import com.goncharenko.currencyexchangerate.mapper.CurrencyRowMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;


@Component
public class CurrencyRepositoryImpl implements CurrencyRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(CurrencyRepositoryImpl.class);
    private final JdbcTemplate jdbcTemplate;
    private final CurrencyRowMapper CURRENCY_ROW_MAPPER;

    public CurrencyRepositoryImpl(JdbcTemplate jdbcTemplate, CurrencyRowMapper CURRENCY_ROW_MAPPER) {
        this.jdbcTemplate = jdbcTemplate;
        this.CURRENCY_ROW_MAPPER = CURRENCY_ROW_MAPPER;
        LOGGER.debug("================CurrencyRepository constructor is called===========");
    }

    /**
     * This method retrieves currency by id
     *
     * @param id currency id
     * @return current currency or Optional.empty() if currency with given id not found
     */
    @Override
    public Optional<Currency> retrieveById(Long id) {
        LOGGER.debug("retrieve currency with id {} ", id);
        try {
            return Optional.of(jdbcTemplate.queryForObject
                    ("SELECT * FROM currencies where id = ?", CURRENCY_ROW_MAPPER, id));
        } catch (EmptyResultDataAccessException e) {
            LOGGER.debug("Empty result, no currency found with given ID ");
            return Optional.empty();
        }
    }

    /**
     * This method retrieves all currencies from table.
     *
     * @return List of currencies from table.
     */
    @Override
    public List<Currency> retrieveAll() {
        LOGGER.debug("retrieve all currencies");
        return jdbcTemplate.query
                ("SELECT * FROM currencies", CURRENCY_ROW_MAPPER);
    }

    /**
     * This method retrieves all currencies by given bank_id
     *
     * @param bankId bank ID to which this currencies belongs
     * @return current List of currencies by given bank id.
     */
    @Override
    public List<Currency> retrieveAllCurrenciesByBankId(Long bankId) {
        LOGGER.debug("retrieve all currencies by bank_id");
        return jdbcTemplate.query
                ("SELECT * FROM currencies where bank_id=?", CURRENCY_ROW_MAPPER, bankId);
    }

    /**
     * This method creates currency in table
     *
     * @param bankId   bank ID to which this currency belongs
     * @param currency new Currency object.
     * @return created currency or Optional.empty() if currency was not created
     */
    @Override
    public Optional<Currency> create(Long bankId, Currency currency) {
        LOGGER.debug("creating new Currency with properties {} ", currency);
        LOGGER.info("creating new currency");
        KeyHolder keyHolder = new GeneratedKeyHolder();
        try {
            jdbcTemplate.update(con -> {
                PreparedStatement statement = con.prepareStatement(
                        "INSERT INTO" +
                                " currencies(name, short_name, purchase, sale, bank_id)" +
                                " VALUES(?, ?, ?, ?, ?)", new String[]{"id"});

                statement.setString(1, currency.getName());
                statement.setString(2, currency.getShortName());
                statement.setDouble(3, currency.getPurchase());
                statement.setDouble(4, currency.getSale());
                statement.setLong(5, bankId);
                return statement;
            }, keyHolder);
            long currencyId = keyHolder.getKey().longValue();
            LOGGER.debug("creating new Currency with properties {} ", currency);
            return retrieveById(currencyId);

        } catch (DuplicateKeyException e) {
            LOGGER.debug("Cannot create currency with these fields because they are not unique");
            return Optional.empty();
        }
    }

    /**
     * This method updates currency with given id in table
     *
     * @param id       currency ID which need to change
     * @param currency new Currency object with changed params.
     * @return updated currency or Optional.empty() if currency was not updated.
     */
    @Override
    public Optional<Currency> update(Long id, Currency currency) {
        LOGGER.debug("updating currency with id {} ", id);
        jdbcTemplate.update("UPDATE currencies" +
                        " SET name = ?," +
                        "short_name = ?," +
                        "purchase = ?, " +
                        "sale = ? " +
                        "WHERE id = ?",
                currency.getName(), currency.getShortName(), currency.getPurchase(), currency.getSale(), id);
        return retrieveById(id);
    }

    /**
     * This method deletes currency with given id in table
     *
     * @param id currency ID which need to delete
     */
    @Override
    public void delete(Long id) {
        LOGGER.debug("deleting currency with id {} ", id);
        jdbcTemplate.update("DELETE from currencies  WHERE id = ?", id);
    }

    /**
     * This method deletes all currencies with given bank_id in table
     *
     * @param bankId bank ID to which this currencies belongs
     */
    @Override
    public void deleteAllByBankId(Long bankId) {
        LOGGER.debug("deleting all currencies by bank id{} ", bankId);
        jdbcTemplate.update("DELETE FROM currencies where bank_id = ?", bankId);
    }

    @Override
    public List<Currency> getPaginatedData(int pageNumber, int pageSize) {
        int limit = pageSize;
        int offset = pageNumber * pageSize;
        return jdbcTemplate.query("select * from currencies limit ? offset ?", CURRENCY_ROW_MAPPER, limit, offset);
    }
}
