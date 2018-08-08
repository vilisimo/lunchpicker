package org.lunchpicker.service;

import org.lunchpicker.domain.Winner;
import org.lunchpicker.persistence.WinnerRepository;
import org.lunchpicker.persistence.criteria.WinnerSearchCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.lunchpicker.persistence.specification.WinnerSpecification.between;
import static org.springframework.data.jpa.domain.Specification.where;

@Service
public class WinnerService {

    private static final Logger logger = LoggerFactory.getLogger(WinnerService.class);

    private final WinnerRepository repository;

    public WinnerService(WinnerRepository repository) {
        this.repository = repository;
    }

    public void save(Winner winner) {
        repository.save(winner);
        logger.debug("Saved a restaurant[id={}] as a winner for {}", winner.getId(), winner.getDate());
    }

    public List<Winner> find(WinnerSearchCriteria criteria) {
        return repository.findAll(where(between(criteria.from, criteria.to)),
                new Sort(Sort.Direction.DESC, "date"));
    }
}
