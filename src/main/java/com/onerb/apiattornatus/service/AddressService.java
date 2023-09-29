package com.onerb.apiattornatus.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onerb.apiattornatus.dto.AddressDto;
import com.onerb.apiattornatus.dto.AddressSetMainDto;
import com.onerb.apiattornatus.error.NotFoundAddressException;
import com.onerb.apiattornatus.error.NotFoundPersonException;
import com.onerb.apiattornatus.model.Address;
import com.onerb.apiattornatus.model.Person;
import com.onerb.apiattornatus.repository.AddressRepository;
import com.onerb.apiattornatus.repository.PersonRepository;


@Service
public class AddressService {

	@Autowired
	AddressRepository addressRepository;
	
	@Autowired
	PersonRepository personRepository;

	@Autowired
	ModelMapper modelMapper;

	public Address createAddress(AddressDto addressDto) throws NotFoundPersonException {
		if (addressRepository.count() == 0 ) {// if new address is the first then it is the main
			addressDto.setIsMain(true);
		} 
		else if (addressDto.getIsMain()) {// if new address marked main
			unsetMainAddress(addressDto.getPersonId());
		}
		Address address = modelMapper.map(addressDto, Address.class);
		Optional<Person> person= personRepository.findById(addressDto.getPersonId());
		if(person.isPresent()) {
			address.setPerson(person.get());
		}
		return addressRepository.save(address);
	}

	public List<Address> getAllAddressOfPerson(UUID personId) {

		return addressRepository.findAllByPersonId(personId);
	}

	public Address setMainAddressOfPerson(AddressSetMainDto addressSetMainDto) throws NotFoundAddressException, NotFoundPersonException {
		
		Address newMainAddress = getAddress(addressSetMainDto.getId());
		unsetMainAddress(addressSetMainDto.getPersonId());
		newMainAddress.setIsMain(true);
		return addressRepository.save(newMainAddress);
	}
	
	public Address getMainAddress(UUID personId) throws NotFoundPersonException {
		Optional<Address> address = addressRepository.findAddressByPersonIdAndIsMain(personId, true);
		if (address.isEmpty()) {
			throw new NotFoundPersonException(personId);
		}
		return address.get();
	}

	private Address unsetMainAddress(UUID personId) throws NotFoundPersonException {
		Address oldAddressMain = getMainAddress(personId);
		if (oldAddressMain != null) {
			oldAddressMain.setIsMain(false);
			addressRepository.save(oldAddressMain);
			return oldAddressMain;
		}
		return null;
	}

	private Address getAddress(UUID id) throws NotFoundAddressException {
		Optional<Address> address = addressRepository.findById(id);
		if (address.isEmpty()) {
			throw new NotFoundAddressException(id);
		}
		return address.get();
	}

}
