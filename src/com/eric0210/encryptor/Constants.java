package com.eric0210.encryptor;

public class Constants
{
	public static final String VERSION = "1.00";
	public static final String CONTRIBUTORS = "eric0210";

	public enum CipherAlgorithm
	{
		AES("AES", false, false, false, true, false), AES_DEFAULT("AES", true, true, true, false, false),
		DES("DES", true, false, false, false, false), RSA("RSA", true, true, true, false, true),
		ARCFOUR("ARCFOUR", false, false, false, false, false), BLOWFISH("Blowfish", false, false, false, false, false);
		public String algorithm;
		public boolean supportMode;
		public boolean supportPadding;
		public boolean separated;
		public boolean supportBitBlockInAlgorithm;
		public boolean isAsymmetricKeyEncryption;

		private CipherAlgorithm(String alg, boolean sm, boolean sp, boolean sep, boolean sbitblockinalg, boolean ake)
		{
			this.algorithm = alg;
			supportMode = sm;
			supportPadding = sp;
			separated = sep;
			supportBitBlockInAlgorithm = sbitblockinalg;
			isAsymmetricKeyEncryption = ake;
		}
	}

	public enum CipherMode
	{
		NONE("NONE", false, false), CIPHER_BLOCK_CHAINING("CBC", true, false), CIPHER_FEED_BACK("CFB", true, false),
		COUNTER("CTR", false, true), ELECTRONIC_CODE_BOOK("ECB", false, false), OUTPUT_FEEDBACK("OFB", true, false);
		public String name;
		public boolean needIV;
		public boolean needCounter;

		private CipherMode(String str, boolean iv, boolean ctr)
		{
			name = str;
			needCounter = ctr;
			needIV = iv;
		}
	}

	public enum CipherPadding
	{
		NO_PADDING("NoPadding"), PKCS1("PKCS1Padding"), PKCS5("PKCS5Padding"), OAEP("OAEPPadding");
		public String name;

		private CipherPadding(String n)
		{
			name = n;
		}
	}

	public static String getCipherAlgorithm(CipherAlgorithm alg, int bitblock, CipherMode mode, CipherPadding padding,
			boolean excludemp)
	{
		StringBuilder str = new StringBuilder();
		str.append(alg.algorithm);
		if (excludemp)
			return str.toString();
		if (alg.supportBitBlockInAlgorithm && bitblock != 0)
		{
			str.append('_');
			str.append(String.valueOf(bitblock));
		}
		if (alg.separated)
			str.append('/');
		if (alg.supportMode)
		{
			str.append(mode.name);
			if (alg.separated)
				str.append('/');
			if (alg.supportPadding)
				str.append(padding.name);
		}
		return str.toString();
	}
}
