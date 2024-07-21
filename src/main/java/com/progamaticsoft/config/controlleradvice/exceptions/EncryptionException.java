package com.progamaticsoft.config.controlleradvice.exceptions;

public class EncryptionException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public EncryptionException(Exception exception) {
		super(exception);
	}
}
