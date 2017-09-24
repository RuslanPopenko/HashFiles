package com.rubiconproject.hashfiles;

import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Common abstract class which ensures non repeatable code in the tests.
 */
public abstract class AbstactHashableTest {

    protected String expectedHash;

    /**
     * Tests that in some code fired assertion AssertionError with exact error message,
     *
     * @param runnable        piece of code where should be error
     * @param expectedMessage error message for check
     */
    protected void expectedAssertErrorWithMessage(Runnable runnable, String expectedMessage) {
        boolean errorFired = false;
        try {
            runnable.run();
        } catch (AssertionError e) {
            assertEquals(expectedMessage, e.getMessage());
            errorFired = true;
        }
        assertTrue("AssertionError isn't fired", errorFired);
    }

    /**
     * Tests that hashable hash invocation with null argument produces AssertionError with Charset is null message
     * @param hashable
     */
    protected void expectedAssertErrorWithMessageInvokingHashWithNullCharset(Hashable hashable) {
        expectedAssertErrorWithMessage(
                () -> hashable.hash(null),
                "Charset is null");
    }

    /**
     * Tests of the Hashable hash result equals expectedHash
     * @param hashable
     */
    protected void resultEqualsExpectedHash(Hashable hashable) {
        final String actualHash = hashable.hash(StandardCharsets.UTF_8);
        assertEquals(expectedHash, actualHash);
    }

    /**
     * Tests hashable getName equals expectedName
     * @param hashable
     * @param expectedName
     */
    protected void expectedGetNameOfHashableInstance(Hashable hashable, String expectedName) {
        assertEquals(expectedName, hashable.getName());
    }

    /**
     * Creates temporary folder just like in the /src/main/resources/input.
     * @param temporaryFolder
     * @return
     * @throws IOException
     */
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

    /**
     * Writes string into the file.
     * There is NIO used, but it test, so I think it's applicable.
     * @param str
     * @param file
     * @throws IOException
     */
    protected void writeStringIntoFile(String str, File file) throws IOException {
        Files.write(Paths.get(file.toURI()), str.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Reads file into String
     * There is NIO used, but it test, so I think it's applicable.
     * @param file
     * @return
     * @throws IOException
     */
    protected String readFile(File file) throws IOException {
        final byte[] bytes = Files.readAllBytes(Paths.get(file.toURI()));
        return new String(bytes, StandardCharsets.UTF_8);
    }

}
