package com.rubiconproject.hashfiles;

import com.google.common.hash.Hashing;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileHasherTest extends AbstactHashableTest {

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    private File testFile;

    @Before
    public void initData() throws IOException {
        final String fileContent = "Test content " + System.lineSeparator()
                + "New line " + System.lineSeparator()
                + "one new line ";

        testFile = temporaryFolder.newFile("foo.txt");
        Files.write(Paths.get(testFile.toURI()), fileContent.getBytes(StandardCharsets.UTF_8));
        super.expectedHash = com.google.common.io.Files.asByteSource(testFile).hash(Hashing.sha512()).toString();
    }

    @Test
    public void nullInitializationTest() {
        throwErrorTest(
                () -> new FileHasher(null),
                "File is null");
    }

    @Test
    public void nonExistingFileInitializationTest() {
        throwErrorTest(
                () -> new FileHasher(new File("foo")),
                "File foo doesn't exist");
    }

    @Test
    public void nullCharsetTest() {
        throwErrorTest(
                () -> new FileHasher(testFile).hash(null),
                "Inputted charset is null");
    }

    @Test
    public void hashFileTest() throws Exception {
        hashTest(new FileHasher(testFile));
    }
}
