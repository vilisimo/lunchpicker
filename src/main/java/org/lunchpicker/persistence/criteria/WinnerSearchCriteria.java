package org.lunchpicker.persistence.criteria;

import java.time.LocalDate;

/**
 * A class that holds query criteria.
 *
 * Mainly helps to avoid refactoring of API contract as the size of possible
 * query parameters grows.
 */
public class WinnerSearchCriteria {
    public LocalDate from;
    public LocalDate to;
}
