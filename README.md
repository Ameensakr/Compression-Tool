# Huffman Compression Program

## Overview

This project is a Java-based implementation of Huffman coding, a well-known algorithm for lossless data compression. The program compresses a `.txt` file by building a Huffman Tree based on character frequencies and writing the compressed data in a binary format.

For more details on Huffman coding, you can refer to this [Huffman Coding Tree Blog](https://opendsa-server.cs.vt.edu/ODSA/Books/CS3/html/Huffman.html).

## Features

- **File Handling**: Reads input `.txt` files and validates the format.
- **Huffman Tree Construction**: Builds a Huffman tree using character frequencies from the input text file.
- **Compression**: Converts the original text into a binary format using the Huffman tree, reducing file size.
- **Bit-Level Writing**: Writes compressed binary data to a file using bit-level operations for compact storage.

## How It Works

1. **Read Input**: The program reads an input `.txt` file, calculating the frequency of each character.
2. **Build Huffman Tree**: A min-heap is used to construct the Huffman tree based on character frequencies.
3. **Generate Huffman Codes**: Using depth-first search (DFS), the program assigns binary codes to characters.
4. **Compression**: The input text is compressed by encoding it using the generated Huffman codes.
5. **Write Output**: The program writes the binary data along with necessary metadata to a compressed output file.


1. **Compile**: Use the following command to compile the Java program:
   ```bash
   javac Main.java
   ```

2. **Run**: To run the program and compress a file, use:
   ```bash
   java Main
   ```

3. **Output**: The compressed file will be written to the specified output path.


## Classes and Functions

- **Main**: The entry point of the program that handles file I/O and orchestrates the compression process.
- **HuffTree**: Builds the Huffman tree for encoding.
- **HuffBaseNode, HuffLeafNode, HuffInternalNode**: Classes representing the nodes of the Huffman tree.
- **BitOutputStream**: Utility for writing compressed data at the bit level.

## Dependencies

- Standard Java libraries (e.g., `java.io`, `java.util`).

## Limitations

- Only supports `.txt` files.
- Designed for text compression, not suitable for general file compression.

## License

This project is open-source and available under the MIT License.
