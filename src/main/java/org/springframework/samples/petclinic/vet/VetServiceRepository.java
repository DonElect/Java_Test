package org.springframework.samples.petclinic.vet;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.owner.Owner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface VetServiceRepository extends Repository<VetService, Long> {

	@Query("SELECT DISTINCT service FROM VetService service WHERE service.id = :id")
	@Transactional(readOnly = true)
	Optional<VetService> findById(@Param("id") Long id);

	void save(VetService vetService);
}
