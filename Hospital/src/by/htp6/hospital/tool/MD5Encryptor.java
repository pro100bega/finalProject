package by.htp6.hospital.tool;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Класс, предназначенный для шифрования паролей
 * 
 * Password encrypting class
 * 
 * @author Begench Shamuradov, 2017
 */
public class MD5Encryptor {
	private static final String ALGORITHM = "MD5";
	
	private static final String HASH_FORMAT = "%032x";

	private static int POSITIVE = 1;

	/**
	 * 
	 * @param source
	 * @return Returns encrypted hex representation of source string
	 * @throws NoSuchAlgorithmException
	 */
	public static String getHashCode(String password) throws NoSuchAlgorithmException {
		MessageDigest md5Digest = MessageDigest.getInstance(ALGORITHM);
		BigInteger hashCode = new BigInteger(POSITIVE, md5Digest.digest(password.getBytes()));
		return String.format(HASH_FORMAT, hashCode);
	}
}
