package com.progamaticsoft.utils;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.progamaticsoft.config.controlleradvice.exceptions.EncryptionException;

public class EncryptionUtil {
	private static final String UTF_8 = "UTF-8";
	private static final String AES = "AES";
	private static final String ALGORITHM = "AES/CBC/PKCS5PADDING";
	private String key;
	private String initVector;

	public EncryptionUtil(String key, String initVector) {
		this.key = key;
		this.initVector = initVector;
	}

	public String encrypt(final String value) {
		try {
			final IvParameterSpec iv = new IvParameterSpec(this.initVector.getBytes(UTF_8));
			final SecretKeySpec skeySpec = new SecretKeySpec(this.key.getBytes(UTF_8), AES);

			final Cipher cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

			byte[] encrypted = cipher.doFinal(value.getBytes());
			return Base64.getEncoder().encodeToString(encrypted);
		} catch (Exception e) {
			throw new EncryptionException(e);
		}
	}

	public String decrypt(final String encrypted) {
		try {
			final IvParameterSpec iv = new IvParameterSpec(this.initVector.getBytes(UTF_8));
			final SecretKeySpec skeySpec = new SecretKeySpec(this.key.getBytes(UTF_8), AES);

			final Cipher cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

			final byte[] original = cipher.doFinal(Base64.getDecoder().decode(encrypted));
			return new String(original);
		} catch (Exception e) {
			throw new EncryptionException(e);
		}
	}
}