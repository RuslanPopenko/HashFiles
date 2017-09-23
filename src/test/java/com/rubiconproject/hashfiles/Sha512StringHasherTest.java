package com.rubiconproject.hashfiles;

import com.google.common.hash.Hashing;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static com.rubiconproject.hashfiles.Sha512StringHasher.SHA_512_ALGORITHM;

public class Sha512StringHasherTest {

    private String testString;
    private String expectedHash;

    @Before
    public void initTestData() {
        testString = "Test string";
        expectedHash = Hashing.sha512().hashString(testString, StandardCharsets.UTF_8).toString();
    }

    @Test
    public void sha512MessageDigestInstanceExistTest() throws NoSuchAlgorithmException {
        final MessageDigest sha512Algorithm = MessageDigest.getInstance(SHA_512_ALGORITHM);
        Assert.assertEquals(SHA_512_ALGORITHM, sha512Algorithm.getAlgorithm());
    }

    @Test
    public void nullInitializationTest() {
        boolean errorFired = false;
        try {
            new Sha512StringHasher(null);
        } catch (AssertionError e) {
            Assert.assertEquals("Inputted original string is null", e.getMessage());
            errorFired = true;
        }
        Assert.assertTrue(errorFired);
    }

    @Test
    public void nullCharsetTest() {
        boolean errorFired = false;
        try {
            new Sha512StringHasher(testString).hash(null);
        } catch (AssertionError e) {
            Assert.assertEquals("Inputted charset is null", e.getMessage());
            errorFired = true;
        }
        Assert.assertTrue(errorFired);
    }

    @Test
    public void hashStringTest() {
        final Hashable sha512Hasher = new Sha512StringHasher(testString);
        final String actualHash = sha512Hasher.hash(StandardCharsets.UTF_8);
        Assert.assertEquals(expectedHash, actualHash);
    }
}
