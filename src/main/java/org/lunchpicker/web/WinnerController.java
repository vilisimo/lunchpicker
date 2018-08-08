package org.lunchpicker.web;

import org.lunchpicker.domain.Winner;
import org.lunchpicker.persistence.criteria.WinnerSearchCriteria;
import org.lunchpicker.service.WinnerService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "/winners")
public class WinnerController {

    private WinnerService winners;

    public WinnerController(WinnerService winners) {
        this.winners = winners;
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public List<Winner> getWinners(
            @RequestParam(value = "from", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(value = "to", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {

        WinnerSearchCriteria criteria = new WinnerSearchCriteria();
        criteria.from = from;
        criteria.to = to;

        return winners.find(criteria);
    }
}
