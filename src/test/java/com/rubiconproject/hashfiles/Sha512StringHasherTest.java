package com.rubiconproject.hashfiles;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static com.rubiconproject.hashfiles.Sha512StringHasher.SHA_512_ALGORITHM;

public class Sha512StringHasherTest extends AbstactHashableTest {

    private String testString;

    @Before
    public void initTestData() {
        testString = "Nullam ornare, magna ac tincidunt congue, diam nisl vulputate velit, sed auctor nibh ex quis nisi. Duis suscipit purus a elementum sollicitudin. Morbi non eros vitae diam ornare auctor quis ac ipsum. Vestibulum et odio vitae mauris luctus imperdiet at quis nulla. Proin sem sapien, vestibulum sit amet vehicula vitae, sodales nec lacus. Praesent eget aliquet sem. Fusce malesuada semper luctus. Integer hendrerit neque erat, vitae dignissim justo vestibulum vel. Morbi eu purus a elit vulputate congue. Vivamus congue mattis quam et eleifend. Maecenas hendrerit elit non rutrum cursus. Vestibulum sodales arcu quis nisl ultricies maximus. Quisque turpis eros, pellentesque et nisl sit amet, elementum mollis eros. In ut massa sed lorem volutpat egestas sed elementum ex.\n";
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
        super.expectedHash = "af371785c4fecf30acdd648a7d4d649901eeb67536206a9f517768f0851c0a06616f724b2a194e7bc0a762636c55fc34e0fcaf32f1e852682b2b07a9d7b7a9f9";
        hashTest(new Sha512StringHasher(testString));
    }
}
