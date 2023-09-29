package com.onerb.apiattornatus.controller;

import static org.mockito.Mockito.mock;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.onerb.apiattornatus.dto.PersonDto;
import com.onerb.apiattornatus.model.Person;
import com.onerb.apiattornatus.repository.PersonRepository;
import com.onerb.apiattornatus.service.PersonService;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import org.springframework.http.MediaType;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@ExtendWith(MockitoExtension.class)
public class PersonControllerTest {

	@InjectMocks
	PersonController personController;

	@Mock
	PersonRepository personRepository;

	@Mock
	PersonService personService;

	@InjectMocks
	ModelMapper modelMapper = mock(ModelMapper.class);

	MockMvc mockMvc;

	private PersonDto personDto;
	private PersonDto personDtoEdit;
	private Optional<Person> person;
	private UUID id;
	private final static String URL = "/person";
	private ObjectWriter ow;

	@BeforeEach
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(personController).build();
		LocalDate date = LocalDate.of(1998, 11, 23);
		id = UUID.fromString("67393881-108f-4d2e-8cde-0fddaf78fd52");
		personDto = new PersonDto(id, "Breno", date);
		personDtoEdit = new PersonDto(id, "Breno Edit", date);
		person = Optional.of(new Person(id, "Breno", date));
		ow = new ObjectMapper().registerModule(new JavaTimeModule()).writer().withDefaultPrettyPrinter();
	}

	@Test
	public void shouldCreatePersonByIdSuccessfully() throws Exception {
		String content = ow.writeValueAsString(personDto);
		mockMvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON).content(content))
				.andExpect(status().isCreated()).andReturn();
	}

	@Test
	public void shouldGetOnePersonSuccessfully() throws Exception {
		Mockito.lenient().when(personService.getOnePerson(id)).thenReturn(person.get());

		mockMvc.perform(get(URL + "/" + id).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andReturn();
	}

	@Test
	public void shouldGetAllPersonSuccessfully() throws Exception {
		Mockito.lenient().when(personService.getAllPerson()).thenReturn(Collections.singletonList(person.get()));

		mockMvc.perform(get(URL).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	@Test
	public void shouldEditPersonSuccessfully() throws Exception {
		Mockito.lenient().when(personService.createPerson(personDto)).thenReturn(person.get());
		Mockito.lenient().when(personRepository.findById(personDto.getId())).thenReturn(person);

		String content = ow.writeValueAsString(personDtoEdit);
		mockMvc.perform(put(URL).contentType(MediaType.APPLICATION_JSON).content(content)).andExpect(status().isOk())
				.andReturn();

	}
}
