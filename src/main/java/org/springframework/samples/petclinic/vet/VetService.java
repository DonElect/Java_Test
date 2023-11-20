package org.springframework.samples.petclinic.vet;

import jakarta.persistence.*;

@Entity
@Table(name = "vet_service")
public class VetService {
	// vet_service (fields: id, vet_id, service_name, service_price)

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE,
	 					  CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinColumn(name = "vet_id")
	private Vet vet;

	@Column(name = "service_name")
	private String serviceName;

	@Column(name = "service_price")
	private Double servicePrice;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Vet getVet() {
		return vet;
	}

	public void setVet(Vet vet) {
		this.vet = vet;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public Double getServicePrice() {
		return servicePrice;
	}

	public void setServicePrice(Double servicePrice) {
		this.servicePrice = servicePrice;
	}
}
