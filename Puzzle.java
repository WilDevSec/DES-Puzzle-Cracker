import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.crypto.SecretKey;

/**
 * 
 * @author Wil Hutchens - 955525
 *
 */

public class Puzzle {

	private int puzzleNumber;
	private SecretKey secretKey;

	public Puzzle(int puzzleNumber, SecretKey secretKey) {
		this.puzzleNumber = puzzleNumber;
		this.secretKey = secretKey;
	}

	public int getPuzzleNumber() {
		return puzzleNumber;
	}

	public SecretKey getKey() {
		return secretKey;
	}

	/**
	 * Returns a the puzzle in specified byte format, 16 bytes of preceding zeros,
	 * puzzle number then key
	 * 
	 * @return byte[] Entire puzzle in bytes
	 */
	public byte[] getPuzzleAsBytes() {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		try {
			outputStream.write(new byte[16]);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			outputStream.write(CryptoLib.smallIntToByteArray(puzzleNumber));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			outputStream.write(secretKey.getEncoded());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			outputStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return outputStream.toByteArray();
	}

}