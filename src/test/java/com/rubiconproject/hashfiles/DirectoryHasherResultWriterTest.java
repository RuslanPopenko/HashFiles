package com.rubiconproject.hashfiles;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;

public class DirectoryHasherResultWriterTest extends AbstactHashableTest {

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    private File testDirectory;

    @Before
    public void initTestDirectory() throws IOException {
        testDirectory = createTestDirectory(temporaryFolder);
    }

    @Test
    public void nullInitializationTest() {
        throwErrorTest(
                () -> new DirectoryHasherResultWriter(null),
                "Directory hasher is null");
    }

    @Test
    public void anotherNameTest() {
        final File fooDir = new File(testDirectory, "foo");
        fooDir.mkdir();
        throwErrorTest(
                () -> new DirectoryHasherResultWriter(new DirectoryHasher(testDirectory)).write(fooDir),
                "Directory name is not \"output\"");
    }

    @Test
    public void createWithExistingFolder() throws IOException {
        final File output = temporaryFolder.newFolder("output");
        new DirectoryHasherResultWriter(new DirectoryHasher(testDirectory)).write(output);
        checkDirectoryHashResult(output);
    }

    @Test
    public void createWithNonExistingFolder() throws IOException {
        final File output = new File(testDirectory.getParentFile(), "output");
        new DirectoryHasherResultWriter(new DirectoryHasher(testDirectory)).write(output);
        checkDirectoryHashResult(output);
    }

    private void checkDirectoryHashResult(File output) throws IOException {
        Assert.assertTrue("Directory output doesn't exist", output.exists());
        final File results = new File(output, "results.txt");
        Assert.assertTrue("results.txt doesn't exist", results.exists());
        final String expectedFileContent = new StringBuilder("/input")
                .append(System.lineSeparator())
                .append("6dd415b8f89a52dd3ce277946150f1df6ea98a89296d0574db69b1fbc4d0aade51abba041529309abfbf07897808edb31a4a6b73a9b7c79fce20476062f6288a")
                .append(System.lineSeparator())
                .append("/input/bar")
                .append(System.lineSeparator())
                .append("6ef01eac687a58a3b28d924f3fa0641b7629356dfca436beb457424d649d4a64faf60b228c3738a3c75da49052264c92135f8aa296cdaad0d4800a7496f88e62")
                .append(System.lineSeparator())
                .append("/input/bar/fileA.dat")
                .append(System.lineSeparator())
                .append("af371785c4fecf30acdd648a7d4d649901eeb67536206a9f517768f0851c0a06616f724b2a194e7bc0a762636c55fc34e0fcaf32f1e852682b2b07a9d7b7a9f9")
                .append(System.lineSeparator())
                .append("/input/bar/fileB.dat")
                .append(System.lineSeparator())
                .append("46868d0a185e942d2fd15739b60096feab4ccdc99139cca4c9db82325606115c8803a6bffe37d6e54c791330add6e1fc861bfa79399f01cc88eed3fcedce13d4")
                .append(System.lineSeparator())
                .append("/input/bar/fileC.dat")
                .append(System.lineSeparator())
                .append("c1e42aa0c8908c9c3d49879a4fc04a59a755735418ddc3a200e911673da188bf46f67818972eac54b38422895391c82b2b0e0cf34aea9468c3ad73c2d0ffa912")
                .append(System.lineSeparator())
                .append("/input/faz")
                .append(System.lineSeparator())
                .append("9f0c752149eb2699f077215798e03f16837ec85fda55a57efeee6480e8ee43971092deec7ff553476d53f0760d637d41b2c31be2b4ef55614ab5d17ab0f8f6dc")
                .append(System.lineSeparator())
                .append("/input/faz/fileD.dat")
                .append(System.lineSeparator())
                .append("9dd88c920d86ac24112eb692e87b047bb6e69cd413593b009af62a29a71daa68f094dd3340976ae9b8e5d8e5d66d964179409c049103f91f3ccba80d9de63b7a")
                .append(System.lineSeparator())
                .append("/input/faz/fileE.dat")
                .append(System.lineSeparator())
                .append("40c9964826072dbebe00ea99db34a8c8268088738de8d2a9c02743d0eed36a018adf122bacd789cc569ba2f5f54c75191683e3f252486bf71a5824ae99e20017")
                .append(System.lineSeparator())
                .toString();
        Assert.assertEquals(expectedFileContent, readFile(results));
    }
}
