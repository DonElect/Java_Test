package org.springframework.samples.petclinic.owner;

import org.springframework.samples.petclinic.AnimalApi;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;

@Controller
public class AnimalApiController {

	private final AnimalApi animalApi = new AnimalApi();

	@GetMapping("/animal_api")
	public String searchAnimal(Model model) throws IOException {
		System.out.println("I was here");
		String animalName = (String) model.getAttribute("animalName");

		String response = animalApi.searchAnimal(animalName == null ? "cat": animalName);

		model.addAttribute("animalApi", response.isEmpty() ? "" : response);

		return "pets/animalStats";
	}
}
