package com.rightmove.demo.controller;

import java.net.URI;

import com.rightmove.demo.repository.ObjectNotFoundException;
import com.rightmove.demo.service.PersonService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller("/person")
public class PersonController {

	private static final String GET_PERSON_BY_ID_URL = "/person/%s";
	private final PersonService personService;

	@Get("/{id}")
	public HttpResponse<PersonDto> get(@PathVariable String id) {
		try {
			PersonDto personDto = personService.getPerson(id);
			return HttpResponse.ok(personDto);
		} catch (ObjectNotFoundException e) {
			return HttpResponse.notFound();
		}
	}

	@Post
	public HttpResponse<PersonDto> storePerson(@Body PersonDto personDto) {
		String id = personService.storePerson(personDto);
		return HttpResponse.created(URI.create(String.format(GET_PERSON_BY_ID_URL, id)));
	}
}
