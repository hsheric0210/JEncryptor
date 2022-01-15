package com.eric0210.encryptor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.eric0210.encryptor.exceptions.ArgumentException;
import com.eric0210.encryptor.exceptions.UnexpectedException;
import com.eric0210.encryptor.exceptions.WrongKeyException;

public class Cryptor
{
	private SessionInfo info;
	private File input;
	private File output;
	private PrivateKey privateKey;
	private PublicKey publicKey;
	private SecretKey secretkey;
	private boolean isEncryptionMode;

	public Cryptor(SessionInfo info)
	{
		this.info = info;
		input = info.input;
		output = info.output;
		isEncryptionMode = info.encrypt;
	}

	public void loadCustomKey() throws ArgumentException, WrongKeyException, UnexpectedException
	{
		System.out.println("Loading custom key...");
		try
		{
			File f = new File(info.customKeyPath);
			if (!f.exists())
				throw new ArgumentException("Specified custom key file doesn't exists!");
			byte[] keyBytes = Files.readAllBytes(f.toPath());
			if (info.decode_custom_key)
			{
				System.out.println("Decoding key with Base64...");
				keyBytes = Base64.getDecoder().decode(keyBytes);
			}
			if (info.calgorithm.isAsymmetricKeyEncryption)
			{
//					if (info.isCustomKeyIsPublic)
				try
				{
					X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
					publicKey = KeyFactory.getInstance(info.calgorithm.algorithm).generatePublic(spec);
					info.usePublic = true;
					System.out.println("Asymmetric Encryption Key Type: Public Key");
				}
				catch (Exception ex)
				{
					try
					{
						PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
						privateKey = KeyFactory.getInstance(info.calgorithm.algorithm).generatePrivate(spec);
						info.usePublic = false;
						System.out.println("Asymmetric Encryption Key Type: Private Key");
					}
					catch (Exception e)
					{
						throw new WrongKeyException("Unknown key type for asymmetric key encryption", e);
					}
				}
			}
			else
			{
				try
				{
					SecretKeySpec sc = new SecretKeySpec(keyBytes, info.calgorithm.algorithm);
					secretkey = SecretKeyFactory.getInstance(info.calgorithm.algorithm).generateSecret(sc);
				}
				catch (NoSuchAlgorithmException ex)
				{
					secretkey = new SecretKeySpec(keyBytes, info.calgorithm.algorithm);
				}
			}
		}
		catch (IOException ex)
		{
			throw new UnexpectedException("Unexpected I/O Exception", ex);
		}
		catch (InvalidKeySpecException e)
		{
			throw new WrongKeyException("Invalid Key Spec", e);
		}
	}

	public void announceKey()
	{
		info.generatedPublic = publicKey != null ? publicKey.getEncoded() : null;
		info.generatedPrivate = privateKey != null ? privateKey.getEncoded() : null;
		info.generatedSecret = secretkey != null ? secretkey.getEncoded() : null;
	}

	public void generateKeys() throws UnexpectedException
	{
		System.out.println("Generating Encryption key...");
		try
		{
			if (info.calgorithm.isAsymmetricKeyEncryption)
			{
				KeyPairGenerator kg = KeyPairGenerator.getInstance(info.calgorithm.algorithm);
				kg.initialize(info.bitblocksize, new SecureRandom());
				KeyPair kp = kg.generateKeyPair();
				privateKey = kp.getPrivate();
				publicKey = kp.getPublic();
				System.out.println("Generated public key: \"" + new String(publicKey.getEncoded()) + "\"");
				System.out.println("Generated private key: \"" + new String(privateKey.getEncoded()) + "\"");
			}
			else
			{
				KeyGenerator kg = KeyGenerator.getInstance(info.calgorithm.algorithm);
				kg.init(info.bitblocksize, new SecureRandom());
				secretkey = kg.generateKey();
				System.out.println("Generated secret key: \"" + new String(secretkey.getEncoded()) + "\"");
			}
		}
		catch (NoSuchAlgorithmException e)
		{
			throw new UnexpectedException("No such algorithm named \'" + info.calgorithm.algorithm + "\'");
		}
	}

	public void execute() throws WrongKeyException, ArgumentException, UnexpectedException
	{
		if (info.useCustomKey || !info.encrypt)
			loadCustomKey();
		else
			generateKeys();
		if (isEncryptionMode)
		{
			doEncrypt();
			announceKey();
		}
		else
		{
			doDecrypt();
		}
	}

	public void doEncrypt() throws UnexpectedException, ArgumentException, WrongKeyException
	{
		System.out.println("Encrypting...");
		try
		{
			Cipher cipher = Cipher.getInstance(Constants.getCipherAlgorithm(info.calgorithm, info.bitblocksize,
					info.cmode, info.cpadding, info.excludemp));
			Key key = info.calgorithm.isAsymmetricKeyEncryption ? (info.usePublic ? publicKey : privateKey) : secretkey;
			cipher.init(Cipher.ENCRYPT_MODE, key,
					info.cmode.needIV ? new IvParameterSpec(key.getEncoded(), 0, 16) : null);
			byte[] encrypted = cipher.doFinal(Files.readAllBytes(input.toPath()));
			if (info.encode_output)
			{
				System.out.println("Encoding output with Base64...");
				encrypted = Base64.getEncoder().encode(encrypted);
			}
			System.out.println("Writing output...");
			FileOutputStream fos = new FileOutputStream(output);
			fos.write(encrypted);
			fos.close();
			System.out.println("Done!");
		}
		catch (NoSuchAlgorithmException ex)
		{
			throw new UnexpectedException("No such algorithm named \'" + info.calgorithm.algorithm + "\'", ex);
		}
		catch (NoSuchPaddingException e)
		{
			throw new UnexpectedException("No such padding named \'" + info.cpadding.name + "\'", e);
		}
		catch (InvalidKeyException e)
		{
			throw new WrongKeyException("Invalid Key", e);
		}
		catch (IllegalBlockSizeException e)
		{
			throw new ArgumentException("Illegal bit block size " + info.bitblocksize, e);
		}
		catch (BadPaddingException e)
		{
			throw new ArgumentException("Bad padding", e);
		}
		catch (IOException e)
		{
			throw new UnexpectedException("Unexpected I/O Exception", e);
		}
		catch (InvalidAlgorithmParameterException e)
		{
			throw new ArgumentException("Unexpected Cipher argument", e);
		}
	}

	public void doDecrypt() throws UnexpectedException, ArgumentException, WrongKeyException
	{
		System.out.println("Decrypting...");
		try
		{
			Cipher cipher = Cipher.getInstance(Constants.getCipherAlgorithm(info.calgorithm, info.bitblocksize,
					info.cmode, info.cpadding, info.excludemp));
			Key key = info.calgorithm.isAsymmetricKeyEncryption ? (info.usePublic ? publicKey : privateKey) : secretkey;
			cipher.init(Cipher.DECRYPT_MODE, key,
					info.cmode.needIV ? new IvParameterSpec(key.getEncoded(), 0, 16) : null);
			byte[] input = Files.readAllBytes(this.input.toPath());
			if (info.decode_input)
			{
				System.out.println("Decoding input with Base64...");
				input = Base64.getDecoder().decode(input);
			}
			byte[] decrypted = cipher.doFinal(input);
			System.out.println("Writing output...");
			FileOutputStream fos = new FileOutputStream(output);
			fos.write(decrypted);
			fos.close();
			System.out.println("Done!");
		}
		catch (NoSuchAlgorithmException ex)
		{
			throw new UnexpectedException("No such algorithm named \'" + info.calgorithm.algorithm + "\'", ex);
		}
		catch (NoSuchPaddingException e)
		{
			throw new UnexpectedException("No such padding named \'" + info.cpadding.name + "\'", e);
		}
		catch (InvalidKeyException e)
		{
			throw new WrongKeyException("Invalid Key", e);
		}
		catch (IllegalBlockSizeException e)
		{
			throw new ArgumentException("Illegal bit block size " + info.bitblocksize, e);
		}
		catch (BadPaddingException e)
		{
			throw new ArgumentException("Bad padding", e);
		}
		catch (IOException e)
		{
			throw new UnexpectedException("Unexpected I/O Exception", e);
		}
		catch (InvalidAlgorithmParameterException e)
		{
			throw new ArgumentException("Unexpected Cipher argument", e);
		}
	}
}
