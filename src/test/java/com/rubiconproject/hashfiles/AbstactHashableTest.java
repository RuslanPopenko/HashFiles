package com.rubiconproject.hashfiles;

import com.google.common.hash.Hashing;
import org.junit.Assert;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

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
        Assert.assertTrue(errorFired);
    }

    protected void hashTest(Hashable hashable) {
        final String actualHash = hashable.hash(StandardCharsets.UTF_8);
        Assert.assertEquals(expectedHash, actualHash);
    }

    protected void setExpectedHashFromFile(File file) throws IOException {
        expectedHash = com.google.common.io.Files.asByteSource(file).hash(Hashing.sha512()).toString();
    }

}
