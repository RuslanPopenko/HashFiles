package com.rubiconproject.hashfiles;

import java.io.File;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.rubiconproject.hashfiles.StaticUtils.validateNotNull;

/**
 * Implementation of <code>Hashable</code>
 * which takes each directory content in alphabetical order,
 * then concatenating the hashed value of each file,
 * then applying SHA-512 hash to the result String.
 * <p>
 * This implementation keeps every sub file as Hashable processed instance.
 *
 * @see Hashable
 * @see Sha512StringHasher
 * @see FileHasher
 */
public class DirectoryHasher implements Hashable {

    private File directory;
    private Charset charset;
    private String hash;
    private List<Hashable> directoryFiles = new ArrayList<>();

    /**
     * Constructs <code>DirectoryHasher</code> based on directory.
     * @param directory
     */
    public DirectoryHasher(File directory) {
        this.directory = directory;
        validateDirectory();
    }

    /**
     * @see Hashable#hash(Charset)
     * @see FileHasher#hash(Charset)
     */
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

    /**
     * Validates directory for null, existing and file is directory
     */
    private void validateDirectory() {
        validateNotNull(directory, "Directory is null");
        assert directory.exists() : "Directory " + directory.getName() + " doesn't exist";
        assert directory.isDirectory() : directory.getName() + " is not a directory";
    }

    /**
     * Gets directory sub files filtered with directory and file sign
     * @return file sub files as array
     */
    private File[] directoryFiles() {
        return directory.listFiles(file -> file.isDirectory() || file.isFile());
    }

    /**
     * Sorts files by name,
     * then applies mapping to file hash,
     * then joins result to single String
     *
     * @param files array of files
     * @return joined hash of files
     */
    private String concatHashesOf(final File[] files) {
        return Arrays
                .stream(files)
                .sorted()
                .map(this::getDirectoryFileHash)
                .collect(Collectors.joining());
    }

    /**
     * Calculates hash of file.
     * Adds each file to processed sub files list.
     * @param file which should be hashed
     * @return hash of file
     */
    private String getDirectoryFileHash(File file) {
        final Hashable hashable = file.isDirectory()
                ? new DirectoryHasher(file) : new FileHasher(file);
        directoryFiles.add(hashable);
        return hashable.hash(charset);
    }

}
