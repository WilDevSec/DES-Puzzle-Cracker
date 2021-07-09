import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

/**
 * 
 * @author Wil Hutchens - 955525
 *
 */

public class Merkle {
	
	static Cipher cipher;
	
	
	public static void main(String[] args) {
		PuzzleCreator creator = new PuzzleCreator();
		creator.createPuzzles();
		try {
			creator.encryptPuzzlesToFile("Alice.bin");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PuzzleCracker cracker = new PuzzleCracker("Alice.bin");
		Puzzle Bobs = cracker.crack(210);
		
		 int puzzleNumber = Bobs.getPuzzleNumber();
		SecretKey key = creator.findKey(puzzleNumber);
		byte[] encryptedPuzzle = null;
		try {
			encryptedPuzzle = encrypt("Testing Merkles Puzzles!", key);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cracker.decryptMessage(encryptedPuzzle);
		
	}
	
	/**
	 * Encrypts a given string with DES using the given Secret Key
	 * 
	 * @param plainText message to be encrypted
	 * @param secretKey key to encrypt message with
	 * @return encrypted message in byte array
	 */
	public static byte[] encrypt(String plainText, SecretKey secretKey) {
		try {
			cipher = Cipher.getInstance("DES");
		} catch (NoSuchAlgorithmException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (NoSuchPaddingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		byte[] plainTextByte = plainText.getBytes();
		try {
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		byte[] encryptedByte = null;
		try {
			encryptedByte = cipher.doFinal(plainTextByte);
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return encryptedByte;
	}
	
}
