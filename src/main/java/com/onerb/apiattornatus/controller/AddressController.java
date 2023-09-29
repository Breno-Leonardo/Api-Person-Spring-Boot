package com.onerb.apiattornatus.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.onerb.apiattornatus.dto.AddressDto;
import com.onerb.apiattornatus.dto.AddressSetMainDto;
import com.onerb.apiattornatus.error.ErrorApi;
import com.onerb.apiattornatus.error.NotFoundAddressException;
import com.onerb.apiattornatus.error.NotFoundPersonException;
import com.onerb.apiattornatus.model.Address;
import com.onerb.apiattornatus.service.AddressService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;

@RestController
@RequestMapping(path = "/address", value = "/address")
@Tag(name = "Address API")
public class AddressController {

	@Autowired
	AddressService addressService;

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Create an address")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Successfully created"),
			@ApiResponse(responseCode = "400", description = "Invalid parameters"),
			@ApiResponse(responseCode = "404", description = "Person at the address does not exist"),
			@ApiResponse(responseCode = "422", description = "Invalid request data"),
			@ApiResponse(responseCode = "500", description = "Error performing the operation"), })
	public ResponseEntity<Object> createAddress(@RequestBody @Valid AddressDto addressDto) {
		Address address;
		try {
			address = addressService.createAddress(addressDto);

		} catch (NotFoundPersonException e) {
			e.printStackTrace();
			ErrorApi error = new ErrorApi(e.getLocalizedMessage(), HttpStatus.NOT_FOUND.value());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(address);
	}

	@GetMapping("/{personId}")
	@Operation(summary = "Get a person's address list, if there is no address returns empty array", method = "GET")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Successfully get"),
			@ApiResponse(responseCode = "400", description = "Invalid parameters"),
			@ApiResponse(responseCode = "422", description = "Invalid request data"),
			@ApiResponse(responseCode = "500", description = "Error performing the operation"), })
	public ResponseEntity<Object> getAllAddressOfPerson(@PathVariable(value = "personId") @Valid UUID personId) {

		return ResponseEntity.status(HttpStatus.OK).body(addressService.getAllAddressOfPerson(personId));
	}

	@GetMapping("/main/{personId}")
	@Operation(summary = "Returns a person's main address", method = "GET")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Successfully get"),
			@ApiResponse(responseCode = "400", description = "Invalid parameters"),
			@ApiResponse(responseCode = "404", description = "Person at the address does not exist"),
			@ApiResponse(responseCode = "422", description = "Invalid request data"),
			@ApiResponse(responseCode = "500", description = "Error performing the operation"), })
	public ResponseEntity<Object> getMainAddressOfPerson(@PathVariable(value = "personId") @Valid UUID personId) {
		Address address;
		try {
			address = addressService.getMainAddress(personId);

		} catch (NotFoundPersonException e) {
			e.printStackTrace();
			ErrorApi error = new ErrorApi(e.getLocalizedMessage(), HttpStatus.NOT_FOUND.value());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
		}
		return ResponseEntity.status(HttpStatus.OK).body(address);
	}

	@Transactional // important to maintain the consistency of the bank
	@PatchMapping(path = "/mainAddress", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Sets the person's new main address and unsets the old one")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Successfully change"),
			@ApiResponse(responseCode = "400", description = "Invalid parameters"),
			@ApiResponse(responseCode = "404", description = "Person at the address does not exist"),
			@ApiResponse(responseCode = "422", description = "Invalid request data"),
			@ApiResponse(responseCode = "500", description = "Error performing the operation"), })
	public ResponseEntity<Object> setMainAddress(@RequestBody @Valid AddressSetMainDto addressSetMainDto)
			throws NotFoundPersonException, NotFoundAddressException {
		Address address;

		try {
			address = addressService.setMainAddressOfPerson(addressSetMainDto);
		} catch (Exception e) {
			e.printStackTrace();
			ErrorApi error = new ErrorApi(e.getLocalizedMessage(), HttpStatus.NOT_FOUND.value());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
		}

		return ResponseEntity.status(HttpStatus.OK).body(address);
	}
}
