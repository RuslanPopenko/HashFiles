package com.rubiconproject.hashfiles;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;

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

        writeStringIntoFile(fileContent, testFile);
        setExpectedHashFromFile(testFile);
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
    public void getNameTest() {
        super.getNameTest(new FileHasher(testFile), "foo.txt");
    }

    @Test
    public void hashFileTest() throws Exception {
        hashTest(new FileHasher(testFile));
    }
}
