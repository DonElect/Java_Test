/*
 * Copyright 2012-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.vet;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.samples.petclinic.owner.Owner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

/**
 * @author Juergen Hoeller
 * @author Mark Fisher
 * @author Ken Krebs
 * @author Arjen Poutsma
 */
@Controller
class VetController {
	static Integer count = 1;
	static Long serviceCount = 1L;

	private final VetRepository vetRepository;
	private final VetServiceRepository vetServiceRepo;

	public VetController(VetRepository clinicService, VetServiceRepository vetServiceRepo) {
		this.vetRepository = clinicService;
		this.vetServiceRepo = vetServiceRepo;
	}

//	@InitBinder
//	public void setAllowedFields(WebDataBinder dataBinder) {
//		dataBinder.setDisallowedFields("id");
//		dataBinder.setAllowedFields("licenceExpirationDate");
//	}

	@ModelAttribute("vet")
	public Vet findVet(@PathVariable(name = "vetId", required = false) Integer vetId) {
		return vetId == null ? new Vet() : this.vetRepository.findById(vetId);
	}

	@GetMapping("/vets.html")
	public String showVetList(@RequestParam(defaultValue = "1") int page, Model model) {
		// Here we are returning an object of type 'Vets' rather than a collection of Vet
		// objects so it is simpler for Object-Xml mapping
		Vets vets = new Vets();
		Page<Vet> paginated = findPaginated(page);
		vets.getVetList().addAll(paginated.toList());
		return addPaginationModel(page, paginated, model);
	}

	private String addPaginationModel(int page, Page<Vet> paginated, Model model) {
		List<Vet> listVets = paginated.getContent();
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", paginated.getTotalPages());
		model.addAttribute("totalItems", paginated.getTotalElements());
		model.addAttribute("listVets", listVets);
		return "vets/vetList";
	}

	private Page<Vet> findPaginated(int page) {
		int pageSize = 5;
		Pageable pageable = PageRequest.of(page - 1, pageSize);
		return vetRepository.findAll(pageable);
	}

//	@GetMapping({ "/vets" })
//	public @ResponseBody Vets showResourcesVetList() {
//		// Here we are returning an object of type 'Vets' rather than a collection of Vet
//		// objects so it is simpler for JSon/Object mapping
//		Vets vets = new Vets();
//		vets.getVetList().addAll(this.vetRepository.findAll());
//		return vets;
//	}

	@GetMapping("/vets/logins")
	public String loginOwner(Map<String, Object> model) {
		Vet vet = new Vet();
		model.put("vet", vet);
		return "/vets/loginVet";
	}

	@PostMapping("/vets/login")
	public String validateLogin(@Valid Vet vet, BindingResult result, Model model, HttpServletRequest request){

		Optional<Vet> response = this.vetRepository.findByEmail(vet.getEmail());

		if (response.isEmpty() || !response.get().getPassword().equals(vet.getPassword())) {
			return "/vets/loginVet";
		}

		HttpSession session = request.getSession();

		session.setAttribute("vetId", response.get().getId());

		model.addAttribute("vet", response.get());

		return "vets/vetDetails";
	}

	@GetMapping("/vets/service/{vetId}")
	public String showVetServices(@PathVariable Integer vetId, Model model){

		Optional<Vet> vet = Optional.ofNullable(vetRepository.findById(vetId));

		model.addAttribute("listService", vet.get().getVetServices());

		return "vets/services";
	}

	@GetMapping("/vets/new")
	public String initCreationForm(Map<String, Object> model) {
		Vet vet = new Vet();
		model.put("vet", vet);
		return "vets/createOrUpdateVetForm";
	}

	@PostMapping("/vets/new")
	public String processCreationForm(@Valid Vet vet, BindingResult result) {
		vet.setId(count++);
		if (result.hasErrors()) {
			return "vets/createOrUpdateVetForm";
		}

//		vet.setLicenceExpirationDate(LocalDate.parse("2026-12-30"));

		this.vetRepository.save(vet);
		return "redirect:/vets/logins";
	}

	@GetMapping("/vet_service/new")
	public String createService(Map<String, Object> model){
		VetService service = new VetService();

		model.put("vetService", service);

		return "vets/createVetServices";
	}

	@PostMapping("/vet_service/new")
	public String saveService(@Valid VetService vetService, BindingResult result, HttpSession session, Model model) {
		vetService.setId(serviceCount++);
		if (result.hasErrors()) {
			return "vets/createVetServices";
		}

		Integer vetId = (Integer) session.getAttribute("vetId");

		Optional<Vet> vet = Optional.ofNullable(vetRepository.findById(vetId));

		vet.get().addVetService(vetService);

		vetRepository.save(vet.get());

//		this.vetServiceRepo.save(vetService);


		Optional<Vet> savedVet = Optional.ofNullable(vetRepository.findById(vetId));


		model.addAttribute("vet", savedVet.get());

		return "vets/vetDetails";
	}

}
