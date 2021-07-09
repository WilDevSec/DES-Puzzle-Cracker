import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * 
 * @author Wil Hutchens - 955525
 *
 */

public class PuzzleCreator {

	ArrayList<Puzzle> puzzles = new ArrayList<Puzzle>(4096);

	public PuzzleCreator() {

	}

	/**
	 * Creates an arr list of 4096 puzzles with ascending puzzle number and random
	 * key
	 * 
	 * @return Array list of puzzles
	 */
	public ArrayList<Puzzle> createPuzzles() {
		for (int i = 1; i <= 4096; i++) {
			try {
				puzzles.add(new Puzzle(i, CryptoLib.createKey(createRandomKey())));
			} catch (InvalidKeyException | InvalidKeySpecException | NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return puzzles;
	}

	/**
	 * Creates a byte array starting with 48 zeros total of 64 bits/16 bytes, to be
	 * used for DES
	 * 
	 * @return byte array
	 */
	public byte[] createRandomKey() {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		try {
			outputStream.write(CryptoLib.smallIntToByteArray((int) (Math.random() * 65536)));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		byte[] zeros = new byte[6];
		try {
			outputStream.write(zeros);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return outputStream.toByteArray();
	}

	/**
	 * Encrypts a puzzle object using DES
	 * 
	 * @param key    - byte array that is turned into a secret key for encryption
	 * @param puzzle - puzzle to encrypt
	 * @return byte[] encrypted puzzle
	 */
	public byte[] encryptPuzzle(byte[] key, Puzzle puzzle) {
		Cipher cipher = null;
		try {
			cipher = Cipher.getInstance("DES");
		} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key, 0, key.length, "DES"));
		} catch (InvalidKeyException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			return cipher.doFinal(puzzle.getPuzzleAsBytes());
		} catch (IllegalBlockSizeException | BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Encrypts each puzzle in the array list and writes to a given file
	 * 
	 * @param file
	 */
	public void encryptPuzzlesToFile(String file) {
		FileOutputStream outputStream = null;
		try {
			outputStream = new FileOutputStream(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (Puzzle puzzle : puzzles) {
			try {
				outputStream.write(encryptPuzzle((puzzle.getKey().getEncoded()), puzzle));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			outputStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Returns the secret key of the puzzle indexed by given puzzle number
	 * 
	 * @param puzzle number
	 * @return Secret key
	 */
	public SecretKey findKey(int puzzleNumber) {
		return puzzles.get(puzzleNumber - 1).getKey();
	}

}
