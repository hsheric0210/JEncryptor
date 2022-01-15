package com.eric0210.encryptor.exceptions;

import java.security.KeyException;

public class WrongKeyException extends KeyException
{


	private static final long serialVersionUID = 2392384996353921509L;

	public WrongKeyException(String string, Throwable e)
	{
		super(string,e);
	}
	
	public WrongKeyException(String string)
	{
		super(string);
	}
}
