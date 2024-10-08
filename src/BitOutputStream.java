import java.io.*;

public class BitOutputStream implements AutoCloseable {
    private FileOutputStream fileOut;
    private int currentByte;
    private int numBitsFilled;

    public BitOutputStream(FileOutputStream output) {
        fileOut = output;
        currentByte = 0;
        numBitsFilled = 0;
    }

    // Write a single bit (0 or 1)
    public void writeBit(boolean bit) throws IOException {
        if (bit) {
            currentByte |= (1 << (7 - numBitsFilled));
        }
        numBitsFilled++;

        if (numBitsFilled == 8) {
            flush();
        }
    }

    // Write a boolean array as bits
    public void writeBits(boolean[] bits) throws IOException {
        for (boolean bit : bits) {
            writeBit(bit);
        }
    }

    // Write a character (16 bits)
    public void writeChar(char value) throws IOException {
        fileOut.write((value >> 8) & 0xFF);  // Write the higher byte
        fileOut.write(value & 0xFF);         // Write the lower byte
    }

    // Flush the current byte if there are remaining bits
    private void flush() throws IOException {
        if (numBitsFilled > 0) {
            fileOut.write(currentByte);
            currentByte = 0;
            numBitsFilled = 0;
        }
    }

    // Close the stream and flush remaining bits
    @Override
    public void close() throws IOException {
        flush();
        fileOut.close();
    }
}
