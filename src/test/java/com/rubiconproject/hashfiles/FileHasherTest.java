package com.rubiconproject.hashfiles;

import com.google.common.hash.Hashing;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileHasherTest {

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();
    private String fileContent;
    private String expectedHash;
    private File testFile;

    @Before
    public void initData() throws IOException {
        fileContent = "Test content " + System.lineSeparator()
                + "New line " + System.lineSeparator()
                + "one new line ";

        testFile = temporaryFolder.newFile("foo.txt");
        Files.write(Paths.get(testFile.toURI()), fileContent.getBytes(StandardCharsets.UTF_8));
        expectedHash = com.google.common.io.Files.asByteSource(testFile).hash(Hashing.sha512()).toString();
    }

    @Test
    public void nullInitializationTest() {
        boolean errorFired = false;
        try {
            new FileHasher(null);
        } catch (AssertionError e) {
            Assert.assertEquals("File is null", e.getMessage());
            errorFired = true;
        }
        Assert.assertTrue(errorFired);
    }

    @Test
    public void nonExistingFileInitializationTest() {
        boolean errorFired = false;
        try {
            new FileHasher(new File("foo"));
        } catch (AssertionError e) {
            Assert.assertEquals("File foo doesn't exist", e.getMessage());
            errorFired = true;
        }
        Assert.assertTrue(errorFired);
    }

    @Test
    public void nullCharsetTest() {
        boolean errorFired = false;
        try {
            new FileHasher(testFile).hash(null);
        } catch (AssertionError e) {
            Assert.assertEquals("Inputted charset is null", e.getMessage());
            errorFired = true;
        }
        Assert.assertTrue(errorFired);
    }

    @Test
    public void hashFileTest() throws Exception {
        final FileHasher fileHasher = new FileHasher(testFile);
        final String actualHash = fileHasher.hash(StandardCharsets.UTF_8);
        Assert.assertEquals(expectedHash, actualHash);
    }
}
