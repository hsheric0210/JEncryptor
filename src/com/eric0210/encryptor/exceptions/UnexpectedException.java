package com.eric0210.encryptor.exceptions;

public class UnexpectedException extends Exception
{
	private static final long serialVersionUID = 7068818117137657559L;

	public UnexpectedException(String string, Throwable ex)
	{
		super(string, ex);
	}

	public UnexpectedException(String string)
	{
		super(string);
	}
}
