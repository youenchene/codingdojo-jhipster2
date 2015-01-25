package youen.dojo.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import youen.dojo.repository.WeatherRepository;
import youen.dojo.repository.data.Weather;
import youen.dojo.repository.data.WeatherDetail;

import javax.inject.Inject;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class WeatherServiceTest {

    @Mock
    WeatherRepository weatherRepository;

    @InjectMocks
    WeatherService weatherService=new WeatherService();

    @Test
    public void should_return_sunny() {
        //GIVEN
        Weather w=new Weather();
        WeatherDetail wd=new WeatherDetail();
        wd.main="Sunny";
        w.weather=new ArrayList<>();
        w.weather.add(wd);
        when(weatherRepository.getWeather()).thenReturn(w);
        //WHEN
        String result=weatherService.getWeatherSummary();
        //THEN
        assertThat(result).isEqualTo("Sunny");

    }
}
