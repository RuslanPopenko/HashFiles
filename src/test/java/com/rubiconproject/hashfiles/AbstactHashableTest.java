package com.rubiconproject.hashfiles;

import org.junit.Assert;

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

    protected void hashTest(Hashable hashable) {
        final String actualHash = hashable.hash(StandardCharsets.UTF_8);
        Assert.assertEquals(expectedHash, actualHash);
    }

    protected void getNameTest(Hashable hashable, String expectedName) {
        Assert.assertEquals(expectedName, hashable.getName());
    }

    public void writeStringIntoFile(String str, File file) throws IOException {
        Files.write(Paths.get(file.toURI()), str.getBytes(StandardCharsets.UTF_8));
    }

}
