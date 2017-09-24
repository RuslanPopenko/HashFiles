package com.rubiconproject.hashfiles;

import java.io.File;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.rubiconproject.hashfiles.Utils.validateNotNull;

public class DirectoryHasher implements Hashable {

    private File directory;
    private Charset charset;
    private String hash;
    private List<Hashable> directoryFiles = new ArrayList<>();

    public DirectoryHasher(File directory) {
        this.directory = directory;
        validateDirectory();
    }

    @Override
    public String hash(Charset charset) {
        validateNotNull(charset, "Charset is null");

        if (hash != null && this.charset.equals(charset)) {
            return hash;
        }

        this.charset = charset;
        final File[] directoryFiles = directoryFiles();
        final String concatHashOfDirectoryFiles = concatHashesOf(directoryFiles);
        hash = new Sha512StringHasher(concatHashOfDirectoryFiles).hash(charset);
        return hash;
    }

    @Override
    public String getName() {
        return directory.getName();
    }

    public List<Hashable> getDirectoryFiles() {
        return directoryFiles;
    }

    private File[] directoryFiles() {
        return directory.listFiles(file -> file.isDirectory() || file.isFile());
    }

    private String concatHashesOf(final File[] subFiles) {
        return Arrays
                .stream(subFiles)
                .sorted()
                .map(this::getDirectoryFileHash)
                .collect(Collectors.joining());
    }

    private String getDirectoryFileHash(File file) {
        final Hashable hashable = file.isDirectory()
                ? new DirectoryHasher(file) : new FileHasher(file);
        directoryFiles.add(hashable);
        return hashable.hash(charset);
    }

    private void validateDirectory() {
        validateNotNull(directory, "Directory is null");
        assert directory.exists() : "Directory " + directory.getName() + " doesn't exist";
        assert directory.isDirectory() : directory.getName() + " is not a directory";
    }

}
