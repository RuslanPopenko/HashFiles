package com.rubiconproject.hashfiles;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static com.rubiconproject.hashfiles.StaticUtils.validateNotNull;

public class DirectoryHasherResultWriter {

    private final DirectoryHasher directoryHasher;

    private String currentDirContext;
    private PrintWriter printWriter;

    public DirectoryHasherResultWriter(DirectoryHasher directoryHasher) {
        this.directoryHasher = directoryHasher;
        validateNotNull(directoryHasher, "Directory hasher is null");
    }

    public void write(File outputDir) {
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

    private void deleteAndThenCreate(File outputDir) {
        if (outputDir.exists()) {
            outputDir.delete();
        }
        outputDir.mkdir();
    }

    private void printHash(Hashable hashable) {
        printWriter.println(currentDirContext + "/" + hashable.getName());
        printWriter.println(hashable.hash(StandardCharsets.UTF_8));
        if (hashable instanceof DirectoryHasher) {
            printChildrenHashes((DirectoryHasher) hashable);
        }
    }

    private void printChildrenHashes(DirectoryHasher directoryHasher) {
        final String directoryContextSaver = currentDirContext;
        currentDirContext += "/" + directoryHasher.getName();
        final List<Hashable> directoryFiles = directoryHasher.getDirectoryFiles();
        directoryFiles.forEach(this::printHash);
        currentDirContext = directoryContextSaver;
    }

}
