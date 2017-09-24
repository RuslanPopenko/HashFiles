package com.rubiconproject.hashfiles;

import java.io.File;

/**
 * Class which contains program entry point
 *
 * @see DirectoryHasher
 * @see DirectoryHasherResultWriter
 */
public class HashFiles {

    /**
     * Main method takes first argument as path to "input" folder.
     * Creates DirectoryHasher for this input directory.
     * Creates DirectoryHasherResultWriter to the "output" folder, placed at the same level as "input"
     *
     * @param args program arguments
     */
    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("One arguments expected: <path>");
            System.exit(1);
        } else if (!args[0].endsWith("input")) {
            System.err.println("Expected path to \"input\" folder");
            System.exit(1);
        }

        final File inputDirectory = new File(args[0]);
        final DirectoryHasher directoryHasher = new DirectoryHasher(inputDirectory);

        new DirectoryHasherResultWriter(directoryHasher)
                .write(new File(inputDirectory.getParentFile(), "output"));
    }

}
