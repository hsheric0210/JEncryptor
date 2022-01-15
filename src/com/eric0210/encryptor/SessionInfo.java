package com.eric0210.encryptor;

import java.io.File;
import com.eric0210.encryptor.Constants.CipherAlgorithm;
import com.eric0210.encryptor.Constants.CipherMode;
import com.eric0210.encryptor.Constants.CipherPadding;

public class SessionInfo
{
	public boolean encrypt;
	public File input;
	public File output;
	public CipherAlgorithm calgorithm;
	public CipherMode cmode;
	public CipherPadding cpadding;
	public int bitblocksize;
	public boolean useCustomKey;
	public String customKeyPath;
	public byte[] generatedPrivate;
	public byte[] generatedPublic;
	public byte[] generatedSecret;
	public boolean excludemp;
	public boolean usePublic;
	public boolean decode_custom_key;
	public boolean encode_output = false;
	public boolean decode_input = false;
}
