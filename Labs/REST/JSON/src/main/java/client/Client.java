package client;

import com.fasterxml.jackson.databind.ObjectMapper;
import common.Flight;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class Client {

    private static final String BASE_URL = "http://localhost:8080/flights/";
    private static final CloseableHttpClient client = HttpClients.createDefault();

    public static void main(String[] args) throws IOException {

        // GET examples
        System.out.println(getFlight(1));
        Flight flight = getFlight(2);
        System.out.println(flight);

        // POST example
        flight.setName("XYZ012");
        HttpResponse response = updateFlight(flight);
        System.out.println(response);
        System.out.println(getFlight(2));
    }

    private static Flight getFlight(int id) throws IOException {
        System.out.println("GET flight with id " + id);
        ObjectMapper mapper = new ObjectMapper();
        URL url = new URL(BASE_URL + id);
        InputStream input = url.openStream();
        return mapper.readValue(input, Flight.class);
    }

    private static HttpResponse updateFlight(Flight flight) throws IOException {
        System.out.println("POST flight with id " + flight.getId());
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(flight);

        HttpPut httpPut = new HttpPut(BASE_URL + flight.getId());
        StringEntity entity = new StringEntity(json);
        httpPut.setEntity(entity);
        httpPut.setHeader("Accept", "application/json");
        httpPut.setHeader("Content-type", "application/json");
        return client.execute(httpPut);
    }
}