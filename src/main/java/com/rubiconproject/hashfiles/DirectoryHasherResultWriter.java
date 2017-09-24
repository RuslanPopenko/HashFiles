package com.rubiconproject.hashfiles;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static com.rubiconproject.hashfiles.StaticUtils.validateNotNull;

/**
 * Class which writes result to file "results.txt" in the "output" directory.
 * It makes it in the next way.
 * It writes first directoryHasher folder hash, then it starts write sub files hashes.
 * If we have sub directory, it content writes recursively.
 * File information format writes in two line:
 * 1) Name of hashed file structure starts with slash
 * 2) Hash of file structure
 *
 * @see DirectoryHasher
 */
public class DirectoryHasherResultWriter {

    private final DirectoryHasher directoryHasher;

    private String currentDirContext;
    private PrintWriter printWriter;

    /**
     * Constructs <code>DirectoryHasherResultWriter</code> based on <code>DirectoryHasher</code>
     *
     * @param directoryHasher
     */
    public DirectoryHasherResultWriter(DirectoryHasher directoryHasher) {
        this.directoryHasher = directoryHasher;
        validateNotNull(directoryHasher, "Directory hasher is null");
    }

    /**
     * Writes content to results.txt file in the output directory.
     * @param outputDir
     */
    public void write(File outputDir) {
        validateDirectoryName(outputDir);
        deleteAndThenCreate(outputDir);
        final File resultFile = new File(outputDir, "results.txt");
        try (final PrintWriter pw = new PrintWriter(resultFile)) {
            printWriter = pw;
            currentDirContext = "";
            printHash(directoryHasher);
            pw.flush();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * Validate directory name, which should be only "output".
     *
     * @param directory
     */
    private void validateDirectoryName(File directory) {
        assert "output".equals(directory.getName()) : "Directory name is not \"output\"";
    }

    /**
     * Recreates output directory
     * @param outputDir
     */
    private void deleteAndThenCreate(File outputDir) {
        if (outputDir.exists()) {
            outputDir.delete();
        }
        outputDir.mkdir();
    }

    /**
     * Prints hash to results.txt of each file.
     * If we have directory it prints it children.
     * Sorry for instanceof check.
     * I don't want to add getDirectoryFiles of DirectoryHasher to Hashable interface for only this one place.
     * @param hashable file structure which should be printed to file
     */
    private void printHash(Hashable hashable) {
        printWriter.println(currentDirContext + "/" + hashable.getName());
        printWriter.println(hashable.hash(StandardCharsets.UTF_8));
        if (hashable instanceof DirectoryHasher) {
            printChildrenHashes((DirectoryHasher) hashable);
        }
    }

    /**
     * Prints every children hash under parent hash.
     * After children printing it roles back currentDirContext.
     * @param directoryHasher directory.
     */
    private void printChildrenHashes(DirectoryHasher directoryHasher) {
        final String directoryContextSaver = currentDirContext;
        currentDirContext += "/" + directoryHasher.getName();
        final List<Hashable> directoryFiles = directoryHasher.getDirectoryFiles();
        directoryFiles.forEach(this::printHash);
        currentDirContext = directoryContextSaver;
    }

}
