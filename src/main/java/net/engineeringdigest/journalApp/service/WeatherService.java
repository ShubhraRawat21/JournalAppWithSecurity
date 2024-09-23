package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.apiResponse.WeatherResponse;
import net.engineeringdigest.journalApp.cache.AppCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class WeatherService {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    AppCache appCache;

    @Value("${weather.api.key}")
    private String API_KEY;

    private String url = "http://api.weatherstack.com/current?access_key=API_KEY&query=CITY";

    public WeatherResponse getWheather(String city){
            String finalUrl = appCache.app_Cache.get("url").replace("<API_KEY>",API_KEY).replace("<CITY>", city);
            ResponseEntity<WeatherResponse> response = restTemplate.exchange(finalUrl, HttpMethod.GET, null, WeatherResponse.class);
            WeatherResponse body = response.getBody();
            return body;
    }

}
