import java.io.*;

public class BitInputStream implements AutoCloseable {
    private FileInputStream fileIn;
    private int currentByte;
    private int numBitsRemaining;

    public BitInputStream(FileInputStream input) {
        fileIn = input;
        currentByte = 0;
        numBitsRemaining = 0;
    }

    // Read a single bit
    public boolean readBit() throws IOException {
        if (numBitsRemaining == 0) {
            currentByte = fileIn.read();
            if (currentByte == -1) {
                throw new EOFException();  // End of file, no more bits to read
            }
            numBitsRemaining = 8;
        }
        numBitsRemaining--;
        return ((currentByte >> numBitsRemaining) & 1) == 1;
    }

    // Read an array of bits
    public boolean[] readBits(int length) throws IOException {
        boolean[] bits = new boolean[length];
        for (int i = 0; i < length; i++) {
            try {
                bits[i] = readBit();
            } catch (EOFException e) {
                // Stop reading when EOF is encountered
                System.out.println("Reached end of file while reading bits.");
                break;
            }
        }
        return bits;
    }

    public char readChar() throws IOException {
        int high = fileIn.read();  // Read the higher byte
        if (high == -1) {
            throw new EOFException("End of file reached while reading high byte of character.");
        }

        int low = fileIn.read();  // Read the lower byte
        if (low == -1) {
            throw new EOFException("End of file reached while reading low byte of character.");
        }

        return (char) ((high << 8) | low);  // Combine high and low bytes to form the character
    }


    // Close the stream
    @Override
    public void close() throws IOException {
        fileIn.close();
    }
}
