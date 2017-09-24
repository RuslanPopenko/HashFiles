package com.rubiconproject.hashfiles;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static com.rubiconproject.hashfiles.StaticUtils.validateNotNull;

/**
 * Implementation of <code>Hashable</code>
 * which takes SHA-512 hash of string and then hex encode it.
 *
 * @see Hashable
 */
public class Sha512StringHasher implements Hashable {

    public static final String SHA_512_ALGORITHM = "SHA-512";

    private static MessageDigest sha512MessageDigest;
    private String original;

    /**
     * Constructs Sha512StringHasher based on original String which will be encoded.
     *
     * @param original before encoding
     */
    public Sha512StringHasher(String original) {
        validateNotNull(original, "Inputted original string is null");
        this.original = original;
        initializeMessageDigestIfNeeded();
    }

    @Override
    public String hash(Charset charset) {
        validateNotNull(charset, "Charset is null");
        final byte[] hashed = getHashWith(charset);
        return hexEncode(hashed);
    }

    @Override
    public String getName() {
        return original;
    }

    /**
     * Initialize sha512MessageDigest for all class if it wasn't initialized before.
     * This implementation works only in the single thread environment.
     * According to test <code>NoSuchAlgorithmException</code> can be ignored.
     */
    private void initializeMessageDigestIfNeeded() {
        if (sha512MessageDigest == null) {
            try {
                sha512MessageDigest = MessageDigest.getInstance(SHA_512_ALGORITHM);
            } catch (NoSuchAlgorithmException ignored) {}
        }
    }

    /**
     * Gets the hash of original string which encoded with charset
     *
     * @param charset original string charset
     * @return encoded bytes
     */
    private byte[] getHashWith(Charset charset) {
        return sha512MessageDigest.digest(original.getBytes(charset));
    }

    /**
     * Hex encodes every character in the byte array
     * @param hash hashed string as byte array
     * @return hex encoded String of hash String, represented as byte array
     */
    private String hexEncode(byte[] hash) {
        final StringBuilder hexString = new StringBuilder();
        for (byte character : hash) {
            hexString.append(hexEncode(character));
        }
        return hexString.toString();
    }

    /**
     * Hex encode hashed character
     *
     * @param character hashed character
     * @return hexed hashed character
     */
    private String hexEncode(byte character) {
        return Integer.toString((character & 0xff) + 0x100, 16).substring(1);
    }

}
