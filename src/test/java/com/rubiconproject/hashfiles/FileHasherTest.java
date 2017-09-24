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
        final String fileContent = "Nullam ornare, magna ac tincidunt congue, diam nisl vulputate velit, sed auctor nibh ex quis nisi. Duis suscipit purus a elementum sollicitudin. Morbi non eros vitae diam ornare auctor quis ac ipsum. Vestibulum et odio vitae mauris luctus imperdiet at quis nulla. Proin sem sapien, vestibulum sit amet vehicula vitae, sodales nec lacus. Praesent eget aliquet sem. Fusce malesuada semper luctus. Integer hendrerit neque erat, vitae dignissim justo vestibulum vel. Morbi eu purus a elit vulputate congue. Vivamus congue mattis quam et eleifend. Maecenas hendrerit elit non rutrum cursus. Vestibulum sodales arcu quis nisl ultricies maximus. Quisque turpis eros, pellentesque et nisl sit amet, elementum mollis eros. In ut massa sed lorem volutpat egestas sed elementum ex.\n";

        testFile = temporaryFolder.newFile("foo.txt");

        writeStringIntoFile(fileContent, testFile);
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
                () -> new FileHasher(new File(testFile.getParentFile(), "test.txt")),
                "File test.txt doesn't exist");
    }

    @Test
    public void isNotFileTest() {
        final File foo = new File(testFile.getParentFile(), "foo");
        foo.mkdir();
        throwErrorTest(
                () -> new FileHasher(foo),
                "foo is not a file");
    }

    @Test
    public void nullCharsetTest() {
        nullCharsetTest(new FileHasher(testFile));
    }

    @Test
    public void getNameTest() {
        super.getNameTest(new FileHasher(testFile), "foo.txt");
    }

    @Test
    public void hashFileTest() throws Exception {
        super.expectedHash = "af371785c4fecf30acdd648a7d4d649901eeb67536206a9f517768f0851c0a06616f724b2a194e7bc0a762636c55fc34e0fcaf32f1e852682b2b07a9d7b7a9f9";
        hashTest(new FileHasher(testFile));
    }
}
