package youen.dojo.web.rest;

import com.codahale.metrics.annotation.Timed;
import youen.dojo.domain.Time;
import youen.dojo.domain.TimeSummary;
import youen.dojo.repository.TimeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import youen.dojo.service.TimeService;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Time.
 */
@RestController
@RequestMapping("/app")
public class TimeResource {

    private final Logger log = LoggerFactory.getLogger(TimeResource.class);

    @Inject
    private TimeRepository timeRepository;

    @Inject
    private TimeService timeService;

    /**
     * POST  /rest/times -> Create a new time.
     */
    @RequestMapping(value = "/rest/times",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void create(@RequestBody Time time) {
        log.debug("REST request to save Time : {}", time);
        timeRepository.save(time);
    }

    /**
     * GET  /rest/times -> get all the times.
     */
    @RequestMapping(value = "/rest/times",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Time> getAll() {
        log.debug("REST request to get all Times");
        return timeRepository.findAll();
    }

    /**
     * GET  /rest/times/:id -> get the "id" time.
     */
    @RequestMapping(value = "/rest/times/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Time> get(@PathVariable Long id) {
        log.debug("REST request to get Time : {}", id);
        return Optional.ofNullable(timeRepository.findOne(id))
            .map(time -> new ResponseEntity<>(
                time,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /rest/times/:id -> delete the "id" time.
     */
    @RequestMapping(value = "/rest/times/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Time : {}", id);
        timeRepository.delete(id);
    }


    /**
     * GET  /rest/times -> get all the times.
     */
    @RequestMapping(value = "/rest/summary/lastTimesLoggedByUser/{login}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Time> getLastLoggedByLogin(@PathVariable String login) {
        log.debug("REST request to last logged for user {}",login);
        return timeRepository.findLastLoggerByUser(login);
    }

    /**
     * GET  /rest/times -> get all the times.
     */
    @RequestMapping(value = "/rest/summary/daysByUser/{login}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<TimeSummary> getDaysByLogin(@PathVariable String login) {
        log.debug("REST request to days for user {}",login);
        return timeService.getStatsByDay(login);
    }
}
