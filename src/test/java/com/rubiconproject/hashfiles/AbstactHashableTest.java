package com.rubiconproject.hashfiles;

import org.junit.Assert;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public abstract class AbstactHashableTest {

    protected String expectedHash;

    protected void throwErrorTest(Runnable runnable, String expectedMessage) {
        boolean errorFired = false;
        try {
            runnable.run();
        } catch (AssertionError e) {
            Assert.assertEquals(expectedMessage, e.getMessage());
            errorFired = true;
        }
        Assert.assertTrue("AssertionError isn't fired", errorFired);
    }

    protected void nullCharsetTest(Hashable hashable) {
        throwErrorTest(
                () -> hashable.hash(null),
                "Charset is null");
    }

    protected void hashTest(Hashable hashable) {
        final String actualHash = hashable.hash(StandardCharsets.UTF_8);
        Assert.assertEquals(expectedHash, actualHash);
    }

    protected void getNameTest(Hashable hashable, String expectedName) {
        Assert.assertEquals(expectedName, hashable.getName());
    }

    protected File createTestDirectory(final TemporaryFolder temporaryFolder) throws IOException {
        final File testDirectory = temporaryFolder.newFolder("input");

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

        return testDirectory;
    }

    protected void writeStringIntoFile(String str, File file) throws IOException {
        Files.write(Paths.get(file.toURI()), str.getBytes(StandardCharsets.UTF_8));
    }

    protected String readFile(File file) throws IOException {
        final byte[] bytes = Files.readAllBytes(Paths.get(file.toURI()));
        return new String(bytes, StandardCharsets.UTF_8);
    }

}
