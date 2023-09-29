package com.onerb.apiattornatus.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.onerb.apiattornatus.model.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, UUID> {
	
	List<Address> findAllByPersonId(UUID personId);
	
	
	Optional<Address> findAddressByPersonIdAndIsMain(UUID personId,boolean isMain);
}
