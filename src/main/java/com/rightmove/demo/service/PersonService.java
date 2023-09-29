package com.rightmove.demo.service;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import com.rightmove.demo.client.AgifyClient;
import com.rightmove.demo.controller.PersonDto;
import com.rightmove.demo.domain.Person;
import com.rightmove.demo.repository.PersonRepository;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Singleton
@RequiredArgsConstructor
public class PersonService {
	private static final String AGIFY_EXCEPTION_MESSAGE = "Unable to get age, using to 999";

	private final PersonRepository personRepository;
	private final AgifyClient agifyClient;

	public PersonDto getPerson(String id) {
		Person person = personRepository.getById(id);
		return PersonDto.fromPerson(person);
	}

	public String storePerson(PersonDto personDto) {
		long age;
		try {
			age = agifyClient.getAgeForName(personDto.firstName());
		} catch (IOException e) {
			age = 999L;
			log.warn(AGIFY_EXCEPTION_MESSAGE, e);
		} catch (InterruptedException e) {
			age = 999L;
			log.warn(AGIFY_EXCEPTION_MESSAGE, e);
			Thread.currentThread().interrupt();
		}

		Person personEntity = personDto.toPerson(age);
		return personRepository.store(personEntity);
	}
}
