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
    public void initData() throws IOException {
        testDirectory = temporaryFolder.newFolder("input");

        final File barDir = temporaryFolder.newFolder("input", "bar");

        final File fileA = new File(barDir, "fileA.dat");
        fileA.createNewFile();
        final String fileAcontent = "Nullam ornare, magna ac tincidunt congue, diam nisl vulputate velit, sed auctor nibh ex quis nisi. Duis suscipit purus a elementum sollicitudin. Morbi non eros vitae diam ornare auctor quis ac ipsum. Vestibulum et odio vitae mauris luctus imperdiet at quis nulla. Proin sem sapien, vestibulum sit amet vehicula vitae, sodales nec lacus. Praesent eget aliquet sem. Fusce malesuada semper luctus. Integer hendrerit neque erat, vitae dignissim justo vestibulum vel. Morbi eu purus a elit vulputate congue. Vivamus congue mattis quam et eleifend. Maecenas hendrerit elit non rutrum cursus. Vestibulum sodales arcu quis nisl ultricies maximus. Quisque turpis eros, pellentesque et nisl sit amet, elementum mollis eros. In ut massa sed lorem volutpat egestas sed elementum ex.\n";
        writeStringIntoFile(fileAcontent, fileA);

        final File fileB = new File(barDir, "fileB.dat");
        fileB.createNewFile();
        final String fileBcontent = "Fusce vel lobortis nulla. Nullam ut placerat nisl. Mauris facilisis, nulla eget viverra porta, sapien ante finibus justo, ut blandit nulla purus at enim. Pellentesque feugiat euismod tellus, nec viverra urna mattis luctus. Integer eu augue vitae arcu bibendum hendrerit. Maecenas laoreet erat quis odio ullamcorper viverra. Vivamus tellus quam, tristique vel accumsan at, dignissim quis dui. Aliquam nec leo eros. Curabitur dignissim non leo non porttitor. Donec gravida vitae nulla vel fringilla. Vestibulum bibendum elit eu urna efficitur facilisis.\n";
        writeStringIntoFile(fileBcontent, fileB);

        final File fileC = new File(barDir, "fileC.dat");
        fileC.createNewFile();
        final String fileCcontent = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc sollicitudin id purus in tincidunt. Integer elit eros, imperdiet ut ex non, varius pulvinar risus. Ut et magna eu lectus lobortis vulputate sit amet id diam. Quisque mauris eros, bibendum at suscipit vitae, efficitur eget orci. Etiam ut nibh aliquet, convallis lacus ac, malesuada elit. Sed sed tincidunt sapien. Vestibulum id velit nec purus dignissim facilisis.\n";
        writeStringIntoFile(fileCcontent, fileC);

        final File fazDir = temporaryFolder.newFolder("input", "faz");

        final File fileD = new File(fazDir, "fileD.dat");
        fileD.createNewFile();
        final String fileDcontent = "Sed aliquet felis sem, eu aliquam nisl placerat in. Integer placerat semper egestas. Cras mollis risus sed mi ultricies, et aliquet ex lacinia. Curabitur sagittis odio lectus, ut mattis orci pretium vel. In vehicula, libero sit amet maximus tristique, nunc elit bibendum quam, ac volutpat ipsum nulla ut arcu. Aenean ac pellentesque tortor, a pellentesque nulla. Aliquam quis viverra elit, eget pretium elit. Nullam sit amet egestas nisl, id fermentum eros. Praesent at sodales eros. Duis non ipsum quis massa ullamcorper varius eget at nibh. Cras accumsan semper tortor vel feugiat. Fusce blandit, lorem ac luctus venenatis, risus quam congue quam, quis porta turpis nisi eget dui. Etiam quis sagittis elit.\n";
        writeStringIntoFile(fileDcontent, fileD);

        final File fileE = new File(fazDir, "fileE.dat");
        fileE.createNewFile();
        final String fileEcontent = "Ut feugiat mi in malesuada commodo. Vivamus felis justo, vestibulum at ex non, iaculis malesuada lectus. Fusce et scelerisque tortor, sed laoreet neque. Vivamus id imperdiet mi. Nunc placerat, nulla sit amet mattis ullamcorper, lorem ipsum imperdiet ex, vel finibus eros risus nec libero. Integer ac lacinia augue, quis facilisis mauris. Nunc ac mauris non lectus dictum varius a sit amet magna.\n";
        writeStringIntoFile(fileEcontent, fileE);

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
