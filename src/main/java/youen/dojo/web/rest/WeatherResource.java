package youen.dojo.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import youen.dojo.service.WeatherService;

import javax.inject.Inject;

/**
 * REST controller for managing Time.
 */
@RestController
@RequestMapping("/app")
public class WeatherResource {

    private final Logger log = LoggerFactory.getLogger(WeatherResource.class);

    @Inject
    private WeatherService weatherService;


    /**
     * GET  /rest/times -> get all the times.
     */
    @RequestMapping(value = "/rest/weather/current",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public String getCurrent() {
        log.debug("REST request to get weather");
        return weatherService.getWeatherSummary();
    }

}
