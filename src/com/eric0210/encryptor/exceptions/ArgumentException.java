package com.eric0210.encryptor.exceptions;

public class ArgumentException extends RuntimeException
{
	private static final long serialVersionUID = 1896492337700880190L;

	public ArgumentException(String string)
	{
		super(string);
	}

	public ArgumentException(String string, Throwable t)
	{
		super(string, t);
	}
}
