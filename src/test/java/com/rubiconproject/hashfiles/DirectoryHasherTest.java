package com.rubiconproject.hashfiles;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class DirectoryHasherTest extends AbstactHashableTest {

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
                () -> new DirectoryHasher(null),
                "Directory is null");
    }

    @Test
    public void nonExistingFileInitializationTest() {
        throwErrorTest(
                () -> new DirectoryHasher(new File(testDirectory, "foo")),
                "Directory foo doesn't exist");
    }

    @Test
    public void fileInitializationTest() throws IOException {
        final File testFile = new File(testDirectory, "foo.txt");
        testFile.createNewFile();
        throwErrorTest(
                () -> new DirectoryHasher(testFile),
                "foo.txt is not a directory");
    }

    @Test
    public void nullCharsetTest() {
        nullCharsetTest(new DirectoryHasher(testDirectory));
    }

    @Test
    public void getNameTest() {
        super.getNameTest(new DirectoryHasher(testDirectory), "input");
    }

    @Test
    public void expectedEmptyDirectoryFilesBeforeHashFunctionInvoked() {
        Assert.assertTrue(new DirectoryHasher(testDirectory).getDirectoryFiles().isEmpty());
    }

    @Test
    public void directoryFilesOrderTest() {
        final DirectoryHasher testDirectoryHasher = new DirectoryHasher(testDirectory);
        testDirectoryHasher.hash(StandardCharsets.UTF_8);

        final List<Hashable> directoryFiles = testDirectoryHasher.getDirectoryFiles();

        final DirectoryHasher bar = (DirectoryHasher) directoryFiles.get(0);
        Assert.assertEquals("bar", bar.getName());

        final List<Hashable> barDirectoryFiles = bar.getDirectoryFiles();

        Assert.assertEquals("fileA.dat", barDirectoryFiles.get(0).getName());
        Assert.assertEquals("fileB.dat", barDirectoryFiles.get(1).getName());
        Assert.assertEquals("fileC.dat", barDirectoryFiles.get(2).getName());

        final DirectoryHasher faz = (DirectoryHasher) directoryFiles.get(1);
        Assert.assertEquals("faz", faz.getName());

        final List<Hashable> fazDirectoryFiles = faz.getDirectoryFiles();

        Assert.assertEquals("fileD.dat", fazDirectoryFiles.get(0).getName());
        Assert.assertEquals("fileE.dat", fazDirectoryFiles.get(1).getName());
    }

    @Test
    public void hashDirectoryTest() throws Exception {
        final DirectoryHasher testDirectoryHasher = new DirectoryHasher(testDirectory);

        super.expectedHash = "6dd415b8f89a52dd3ce277946150f1df6ea98a89296d0574db69b1fbc4d0aade51abba041529309abfbf07897808edb31a4a6b73a9b7c79fce20476062f6288a";
        hashTest(testDirectoryHasher);

        final List<Hashable> testDirectoryFiles = testDirectoryHasher.getDirectoryFiles();

        super.expectedHash = "6ef01eac687a58a3b28d924f3fa0641b7629356dfca436beb457424d649d4a64faf60b228c3738a3c75da49052264c92135f8aa296cdaad0d4800a7496f88e62";
        final DirectoryHasher barDir = (DirectoryHasher) testDirectoryFiles.get(0);
        hashTest(barDir);

        final List<Hashable> barDirDirectoryFiles = barDir.getDirectoryFiles();

        super.expectedHash = "af371785c4fecf30acdd648a7d4d649901eeb67536206a9f517768f0851c0a06616f724b2a194e7bc0a762636c55fc34e0fcaf32f1e852682b2b07a9d7b7a9f9";
        final Hashable fileA = barDirDirectoryFiles.get(0);
        hashTest(fileA);

        super.expectedHash = "46868d0a185e942d2fd15739b60096feab4ccdc99139cca4c9db82325606115c8803a6bffe37d6e54c791330add6e1fc861bfa79399f01cc88eed3fcedce13d4";
        final Hashable fileB = barDirDirectoryFiles.get(1);
        hashTest(fileB);

        super.expectedHash = "c1e42aa0c8908c9c3d49879a4fc04a59a755735418ddc3a200e911673da188bf46f67818972eac54b38422895391c82b2b0e0cf34aea9468c3ad73c2d0ffa912";
        final Hashable fileC = barDirDirectoryFiles.get(2);
        hashTest(fileC);

        super.expectedHash = "9f0c752149eb2699f077215798e03f16837ec85fda55a57efeee6480e8ee43971092deec7ff553476d53f0760d637d41b2c31be2b4ef55614ab5d17ab0f8f6dc";
        final DirectoryHasher fazDir = (DirectoryHasher) testDirectoryFiles.get(1);
        hashTest(fazDir);

        final List<Hashable> fazDirDirectoryFiles = fazDir.getDirectoryFiles();

        super.expectedHash = "9dd88c920d86ac24112eb692e87b047bb6e69cd413593b009af62a29a71daa68f094dd3340976ae9b8e5d8e5d66d964179409c049103f91f3ccba80d9de63b7a";
        final Hashable fileD = fazDirDirectoryFiles.get(0);
        hashTest(fileD);

        super.expectedHash = "40c9964826072dbebe00ea99db34a8c8268088738de8d2a9c02743d0eed36a018adf122bacd789cc569ba2f5f54c75191683e3f252486bf71a5824ae99e20017";
        final Hashable fileE = fazDirDirectoryFiles.get(1);
        hashTest(fileE);

    }

}
