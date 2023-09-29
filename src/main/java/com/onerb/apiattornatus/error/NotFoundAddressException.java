package com.onerb.apiattornatus.error;

import java.util.UUID;

public class NotFoundAddressException extends Exception {

	private static final long serialVersionUID = 1L;

	public NotFoundAddressException(UUID id) {
		super(String.format("Address with id %s not exist",id));
	}

}
