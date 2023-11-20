package org.springframework.samples.petclinic;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.samples.petclinic.owner.AnimalApiResponse;

public class AnimalApi {

//	public AnimalApiResponse searAnimal(String animalName) throws IOException {
//		URL url = new URL("https://api.api-ninjas.com/v1/animals?name="+animalName);
//		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//		connection.setRequestProperty("accept", "application/json");
//		InputStream responseStream = connection.getInputStream();
//		ObjectMapper mapper = new ObjectMapper();
//		AnimalApiResponse apiResponse = mapper.convertValue(responseStream, AnimalApiResponse.class);
//		JsonNode root = mapper.readTree(responseStream);
//		System.out.println(root.path("fact").asText());
//
//		return apiResponse;
//	}

	public String  searchAnimal(String animalName) throws IOException {
		URL url = new URL("https://api.api-ninjas.com/v1/animals?name="+animalName);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestProperty("accept", "application/json");
		InputStream responseStream = connection.getInputStream();
		ObjectMapper mapper = new ObjectMapper();
		AnimalApiResponse apiResponse = mapper.convertValue(responseStream, AnimalApiResponse.class);
		JsonNode root = mapper.readTree(responseStream);
		System.out.println(root.path("fact").asText());

		return root.path("fact").asText();
	}


}
