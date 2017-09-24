package com.rubiconproject.hashfiles;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;

import static com.rubiconproject.hashfiles.Utils.validateNotNull;

public class FileHasher implements Hashable {

    private File file;
    private Charset charset;
    private String hash;

    public FileHasher(File file) {
        this.file = file;
        validateFile();
    }

    private void validateFile() {
        validateNotNull(file, "File is null");
        assert file.exists() : "File " + file.getName() + " doesn't exist";
        assert file.isFile() : file.getName() + " is not a file";
    }

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
