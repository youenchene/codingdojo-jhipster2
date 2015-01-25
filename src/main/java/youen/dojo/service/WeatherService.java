package youen.dojo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import youen.dojo.repository.WeatherRepository;
import youen.dojo.repository.data.Weather;

import javax.inject.Inject;

@Service
@Transactional
public class WeatherService {

    private final Logger log = LoggerFactory.getLogger(WeatherService.class);

    @Inject
    private WeatherRepository weatherRepository;

    public String getWeatherSummary() {
        Weather w=weatherRepository.getWeather();
        return w.weather.get(0).main;
    }



}
