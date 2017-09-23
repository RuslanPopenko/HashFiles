package com.rubiconproject.hashfiles;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;

import static com.rubiconproject.hashfiles.Utils.validateNotNull;

public class FileHasher implements Hashable {

    private File file;

    public FileHasher(File file) {
        this.file = file;
        validateFile();
    }

    private void validateFile() {
        validateNotNull(file, "File is null");
        assert file.exists() : "File " + file.getName() + " doesn't exist";
        assert file.isFile() : "File " + file.getName() + " is not a file";
    }

    @Override
    public String hash(final Charset charset) {
        validateNotNull(charset, "Inputted charset is null");
        final String fileContent = readFileContent(charset);
        return new Sha512StringHasher(fileContent)
                .hash(charset);
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
