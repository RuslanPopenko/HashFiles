package com.rubiconproject.hashfiles;

import java.nio.charset.Charset;

/**
 * The <code>Hashable</code> interface should be implemented by any
 * class whose instances are intended to be hashed in string with some way.
 */
public interface Hashable {

    /**
     * Get hash of implementation instance
     *
     * @param charset encoding of inputting data and output string
     * @return hash representation of implementation instance
     */
    public String hash(Charset charset);

    /**
     * Get name of hashing structure
     * @return name of hashing structure
     */
    public String getName();

}
