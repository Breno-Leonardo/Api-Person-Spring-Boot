package com.onerb.apiattornatus.controller;

import static org.mockito.Mockito.mock;

import java.time.LocalDate;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.onerb.apiattornatus.dto.AddressDto;
import com.onerb.apiattornatus.model.Person;
import com.onerb.apiattornatus.repository.AddressRepository;
import com.onerb.apiattornatus.repository.PersonRepository;
import com.onerb.apiattornatus.service.AddressService;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;


import org.springframework.http.MediaType;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@ExtendWith(MockitoExtension.class)
public class AddressControllerTest {

	@InjectMocks
	AddressController addressController;

	@Mock
	AddressRepository addressRepository;

	@Mock
	AddressService addressService;
	
	@Mock
	PersonRepository personRepository;

	@InjectMocks
	ModelMapper modelMapper = mock(ModelMapper.class);

	MockMvc mockMvc;

	private AddressDto addressDto;
	private AddressDto addressDtoEdit;
	private Person person;
	private UUID id;
	private final static String URL = "/address";
	private ObjectWriter ow;

	@BeforeEach
	public void setUp() {
		LocalDate date = LocalDate.of(1998, 11, 23);
		id = UUID.fromString("2eae6a49-dbc0-493c-89cc-56f14e039cb2");
		person = new Person(UUID.fromString("12f8af6d-a316-4485-b905-2bd47429020a"), "Breno", date);
		addressDto = new AddressDto(id, "Centro", "48005556", "Cidade Dos Programadores", 0, false, person.getId());
		addressDtoEdit = new AddressDto(id, "Centro Edit", "48005556", "Cidade Dos Programadores", 0, false, person.getId());
		ow = new ObjectMapper().registerModule(new JavaTimeModule()).writer().withDefaultPrettyPrinter();
		mockMvc = MockMvcBuilders.standaloneSetup(addressController).build();
	}

	@Test
	public void shouldCreateAddressByIdSuccessfully() throws Exception {
		String content = ow.writeValueAsString(addressDto);
		mockMvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON).content(content))
				.andExpect(status().isCreated()).andReturn();
	}
	@Test
	public void shouldGetAllAddressSuccessfully() throws Exception {

		mockMvc.perform(get(URL+"/"+id)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());
	}
	
	@Test
	public void shouldGetMainAddress() throws Exception {

		mockMvc.perform(get(URL+"/main/"+id)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());
	}

	@Test
	public void shouldEditAddressSuccessfully() throws Exception {
		
		String content = ow.writeValueAsString(addressDtoEdit);
		mockMvc.perform(patch(URL+"/mainAddress").contentType(MediaType.APPLICATION_JSON).content(content)).andExpect(status().isOk())
				.andReturn();

	}
}
