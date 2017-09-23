package com.rubiconproject.hashfiles;

import com.google.common.hash.Hashing;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static com.rubiconproject.hashfiles.Sha512StringHasher.SHA_512_ALGORITHM;

public class Sha512StringHasherTest extends AbstactHashableTest {

    private String testString;

    @Before
    public void initTestData() {
        testString = "Test string";
        super.expectedHash = Hashing.sha512().hashString(testString, StandardCharsets.UTF_8).toString();
    }

    @Test
    public void sha512MessageDigestInstanceExistTest() throws NoSuchAlgorithmException {
        final MessageDigest sha512Algorithm = MessageDigest.getInstance(SHA_512_ALGORITHM);
        Assert.assertEquals(SHA_512_ALGORITHM, sha512Algorithm.getAlgorithm());
    }

    @Test
    public void nullInitializationTest() {
        throwErrorTest(
                () -> new Sha512StringHasher(null),
                "Inputted original string is null");
    }

    @Test
    public void nullCharsetTest() {
        throwErrorTest(
                () -> new Sha512StringHasher(testString).hash(null),
                "Inputted charset is null");
    }

    @Test
    public void hashStringTest() {
        hashTest(new Sha512StringHasher(testString));
    }
}
