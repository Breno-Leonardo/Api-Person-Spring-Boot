package com.onerb.apiattornatus.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.onerb.apiattornatus.dto.PersonDto;
import com.onerb.apiattornatus.error.ErrorApi;
import com.onerb.apiattornatus.model.Person;
import com.onerb.apiattornatus.service.PersonService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping(path = "/person", value = "/person")
@Tag(name = "Person API")
public class PersonController {

	@Autowired
	PersonService personService;

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, 
            produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Create a person")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Successfully created"),
			@ApiResponse(responseCode = "400", description = "Invalid parameters"),
			@ApiResponse(responseCode = "422", description = "Invalid request data"),
			@ApiResponse(responseCode = "500", description = "Error performing the operation"), })
	public ResponseEntity<Person> createPerson(@RequestBody @Valid PersonDto personDto) {
		return new ResponseEntity<Person>(personService.createPerson(personDto), HttpStatus.CREATED);
	}

	@PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, 
            produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Changes a person")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Successfully change"),
			@ApiResponse(responseCode = "400", description = "Invalid parameters"),
			@ApiResponse(responseCode = "404", description = "Person does not exist"),
			@ApiResponse(responseCode = "422", description = "Invalid request data"),
			@ApiResponse(responseCode = "500", description = "Error performing the operation"), })
	public ResponseEntity<Object> editPerson(@RequestBody @Valid PersonDto personDto) {
		Person person;
		try {
			person = personService.editPerson(personDto);
		} catch (Exception e) {
			e.printStackTrace();
			ErrorApi error = new ErrorApi(e.getLocalizedMessage(), HttpStatus.NOT_FOUND.value());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
		}
		return ResponseEntity.status(HttpStatus.OK).body(person);
	}

	@GetMapping("/{id}")
	@Operation(summary = "Get one person by id", method = "GET")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Successfully get"),
			@ApiResponse(responseCode = "400", description = "Invalid parameters"),
			@ApiResponse(responseCode = "404", description = "Person does not exist"),
			@ApiResponse(responseCode = "422", description = "Invalid request data"),
			@ApiResponse(responseCode = "500", description = "Error performing the operation"), })
	public ResponseEntity<Object> getOnePerson(@PathVariable(value = "id") @Valid UUID id) {
		Person person;
		try {
			person = personService.getOnePerson(id);
		} catch (Exception e) {
			e.printStackTrace();
			ErrorApi error = new ErrorApi(e.getLocalizedMessage(), HttpStatus.NOT_FOUND.value());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
		}
		return ResponseEntity.status(HttpStatus.OK).body(person);
	}

	@GetMapping
	@Operation(summary = "Returns the list of people, if there are no people, returns an empty array", method = "GET")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Successfully get"),
			@ApiResponse(responseCode = "400", description = "Invalid parameters"),
			@ApiResponse(responseCode = "422", description = "Invalid request data"),
			@ApiResponse(responseCode = "500", description = "Error performing the operation"), })
	public ResponseEntity<List<Person>> getAllPerson() {
		return ResponseEntity.status(HttpStatus.OK).body(personService.getAllPerson());
	}
}
