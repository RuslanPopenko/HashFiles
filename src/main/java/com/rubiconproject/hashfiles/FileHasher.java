package com.rubiconproject.hashfiles;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;

import static com.rubiconproject.hashfiles.StaticUtils.validateNotNull;

/**
 * Implementation of <code>Hashable</code>
 * which takes file content and then encodes it.
 * File content encodes with the <code></code>Sha512StringHasher</code>.
 *
 * @see Hashable
 * @see Sha512StringHasher
 */
public class FileHasher implements Hashable {

    private File file;
    private Charset charset;
    private String hash;

    /**
     * Constructs FileHasher based on file which content will be encoded.
     *
     * @param file entire file
     */
    public FileHasher(File file) {
        this.file = file;
        validateFile();
    }

    /**
     * Validates file for null, existing and file is normal file
     */
    private void validateFile() {
        validateNotNull(file, "File is null");
        assert file.exists() : "File " + file.getName() + " doesn't exist";
        assert file.isFile() : file.getName() + " is not a file";
    }

    /**
     * Keeps prev calculated hash.
     *
     * @see Hashable#hash(Charset)
     */
    @Override
    public String hash(final Charset charset) {
        validateNotNull(charset, "Charset is null");

        if (hash != null && this.charset.equals(charset)) {
            return hash;
        }

        this.charset = charset;
        final String fileContent = readFileContent(charset);
        hash = new Sha512StringHasher(fileContent).hash(charset);
        return hash;
    }

    @Override
    public String getName() {
        return file.getName();
    }

    /**
     * Reads file content with FileInputStream as byte array
     * and then transform it to string with certain encoding
     * @param charset file encoding
     * @return String representation of file content
     */
    private String readFileContent(final Charset charset) {
        try (final FileInputStream fis = new FileInputStream(file)) {
            final byte[] data = new byte[(int) file.length()];
            fis.read(data);
            return new String(data, charset);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

}
