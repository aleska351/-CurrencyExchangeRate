package com.goncharenko.currencyexchangerate.dao;

import java.util.List;

public interface Paginated<T> {

    List<T> getPaginatedData(int pageNumber, int pageSize);
}