package youen.dojo.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import youen.dojo.domain.Fake;
import youen.dojo.repository.mongo.FakeRepository;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Fake.
 */
@RestController
@RequestMapping("/app")
public class FakeResource {

    private final Logger log = LoggerFactory.getLogger(FakeResource.class);

    @Inject
    private FakeRepository fakeRepository;

    /**
     * POST  /rest/fakes -> Create a new fake.
     */
    @RequestMapping(value = "/rest/fakes",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void create(@RequestBody Fake fake) {
        log.debug("REST request to save Fake : {}", fake);
        fakeRepository.save(fake);
    }

    /**
     * GET  /rest/fakes -> get all the fakes.
     */
    @RequestMapping(value = "/rest/fakes",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Fake> getAll() {
        log.debug("REST request to get all Fakes");
        return fakeRepository.findAll();
    }

    /**
     * GET  /rest/fakes/:id -> get the "id" fake.
     */
    @RequestMapping(value = "/rest/fakes/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Fake> get(@PathVariable String id) {
        log.debug("REST request to get Fake : {}", id);
        return Optional.ofNullable(fakeRepository.findOne(id))
            .map(fake -> new ResponseEntity<>(
                fake,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /rest/fakes/:id -> delete the "id" fake.
     */
    @RequestMapping(value = "/rest/fakes/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable String id) {
        log.debug("REST request to delete Fake : {}", id);
        fakeRepository.delete(id);
    }
}
