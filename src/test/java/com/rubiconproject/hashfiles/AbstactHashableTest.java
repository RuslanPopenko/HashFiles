package com.rubiconproject.hashfiles;

import org.junit.Assert;

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

}
