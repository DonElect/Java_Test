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

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.xml.bind.annotation.XmlElement;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;
import org.springframework.core.style.ToStringCreator;
import org.springframework.samples.petclinic.model.Person;

import java.time.LocalDate;
import java.util.*;

/**
 * Simple JavaBean domain object representing a veterinarian.
 *
 * @author Ken Krebs
 * @author Juergen Hoeller
 * @author Sam Brannen
 * @author Arjen Poutsma
 */
@Entity
@Table(name = "vets")
public class Vet extends Person {

	@Column(name = "country")
	@NotBlank
	private String country;
	@Column(name = "license_expire_date")
	@NotNull
	private String licenceExpirationDate;

	@Column(name = "email")
	@NotBlank
	private String email;

	@Column(name = "password")
	@NotBlank
	private String password;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "vet", fetch = FetchType.EAGER)
	private Set<VetService> vetService;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "vet_specialties", joinColumns = @JoinColumn(name = "vet_id"),
			inverseJoinColumns = @JoinColumn(name = "specialty_id"))
	private Set<Specialty> specialties;

	protected Set<VetService> getVetService() {
		if (this.vetService == null) {
			this.vetService = new HashSet<>();
		}
		return this.vetService;
	}

	public int getNrOfServices() {
		return getVetService().size();
	}

	public void addVetService(VetService service) {
		if (this.vetService == null) {
			this.vetService = new HashSet<>();
		}
		this.vetService.add(service);

		service.setVet(this);
	}

	@XmlElement
	public List<VetService> getVetServices() {
		List<VetService> sortedSpecs = new ArrayList<>(getVetService());
		PropertyComparator.sort(sortedSpecs, new MutableSortDefinition("serviceName", true, true));
		return Collections.unmodifiableList(sortedSpecs);
	}

	protected Set<Specialty> getSpecialtiesInternal() {
		if (this.specialties == null) {
			this.specialties = new HashSet<>();
		}
		return this.specialties;
	}

	protected void setSpecialtiesInternal(Set<Specialty> specialties) {
		this.specialties = specialties;
	}

	@XmlElement
	public List<Specialty> getSpecialties() {
		List<Specialty> sortedSpecs = new ArrayList<>(getSpecialtiesInternal());
		PropertyComparator.sort(sortedSpecs, new MutableSortDefinition("name", true, true));
		return Collections.unmodifiableList(sortedSpecs);
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getLicenceExpirationDate() {
		return licenceExpirationDate;
	}

	public void setLicenceExpirationDate(String licenceExpirationDate) {
		this.licenceExpirationDate = licenceExpirationDate;
	}

	public int getNrOfSpecialties() {
		return getSpecialtiesInternal().size();
	}

	public void addSpecialty(Specialty specialty) {
		getSpecialtiesInternal().add(specialty);
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	@Override
	public String toString() {
		return new ToStringCreator(this).append("id", this.getId())
			.append("new", this.isNew())
			.append("lastName", this.getLastName())
			.append("firstName", this.getFirstName())
			.append("email", this.getEmail())
			.append("password", this.getPassword())
			.append("country", this.country)
			.append("licenceExpirationDate", this.licenceExpirationDate)
			.toString();
	}
}
