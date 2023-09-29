package com.onerb.apiattornatus.error;

import java.util.UUID;

public class NotFoundPersonException extends Exception {

	private static final long serialVersionUID = 1L;

	public NotFoundPersonException(UUID id) {
		super(String.format("Person with id %s not exist",id));
	}

}
