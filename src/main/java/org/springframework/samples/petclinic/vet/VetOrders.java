package org.springframework.samples.petclinic.vet;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.springframework.samples.petclinic.model.OrderStatus;
import org.springframework.samples.petclinic.model.PaymentMethod;
import org.springframework.samples.petclinic.owner.Pet;
import org.springframework.samples.petclinic.taxes.Taxes;

import java.math.BigDecimal;

@Entity
@Table(name = "vet_orders")
public class VetOrders {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE,
		CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinColumn(name = "pet_id")
	private Pet pet;

	@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE,
		CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinColumn(name = "vet_id")
	private Vet vet;

	@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE,
		CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinColumn(name = "vet_service_id")
	private VetService vetService;

	@Enumerated(EnumType.STRING)
	@Column(name = "payment_method")
	@NotBlank
	private PaymentMethod paymentMethod;

	private BigDecimal amount;

	@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE,
		CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinColumn(name = "tax_id")
	private Taxes tax;

	@Column(name = "total")
	@NotBlank
	private BigDecimal total;

	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	@NotBlank
	private OrderStatus status;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Pet getPet() {
		return pet;
	}

	public void setPet(Pet pet) {
		this.pet = pet;
	}

	public Vet getVet() {
		return vet;
	}

	public void setVet(Vet vet) {
		this.vet = vet;
	}

	public VetService getVetService() {
		return vetService;
	}

	public void setVetService(VetService vetService) {
		this.vetService = vetService;
	}

	public PaymentMethod getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(PaymentMethod paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Taxes getTax() {
		return tax;
	}

	public void setTax(Taxes tax) {
		this.tax = tax;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}
}
