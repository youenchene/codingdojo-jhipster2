package youen.dojo.repository;

import com.github.kevinsawicki.http.HttpRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.stereotype.Repository;
import youen.dojo.repository.data.Weather;

/**
 * Created by youen on 25/01/2015.
 */
@Repository
public class WeatherRepository {

    String uri="http://api.openweathermap.org/data/2.5/weather?q=Rouen,Fr";

    public Weather getWeather() {

        return new Gson().fromJson(HttpRequest.get(uri).body(), new TypeToken<Weather>() {
        }.getType());

    }
}
