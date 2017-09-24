package com.rubiconproject.hashfiles;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static com.rubiconproject.hashfiles.Utils.validateNotNull;

public class Sha512StringHasher implements Hashable {

    public static final String SHA_512_ALGORITHM = "SHA-512";

    private static MessageDigest sha512MessageDigest;
    private String original;

    public Sha512StringHasher(String original) {
        validateNotNull(original, "Inputted original string is null");
        this.original = original;
        initializeMessageDigestIfNeeded();
    }

    @Override
    public String hash(Charset charset) {
        validateNotNull(charset, "Inputted charset is null");
        final byte[] hashed = hashOriginalWith(charset);
        return hexEncode(hashed);
    }

    @Override
    public String getName() {
        return original;
    }

    private void initializeMessageDigestIfNeeded() {
        if (sha512MessageDigest == null) {
            try {
                sha512MessageDigest = MessageDigest.getInstance(SHA_512_ALGORITHM);
            } catch (NoSuchAlgorithmException ignored) {}
        }
    }

    private byte[] hashOriginalWith(Charset charset) {
        return sha512MessageDigest.digest(original.getBytes(charset));
    }

    private String hexEncode(byte[] hash) {
        final StringBuilder hexString = new StringBuilder();
        for (byte value : hash) {
            hexString.append(hexEncode(value));
        }
        return hexString.toString();
    }

    private String hexEncode(byte hash) {
        return Integer.toString((hash & 0xff) + 0x100, 16).substring(1);
    }

}
