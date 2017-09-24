package com.rubiconproject.hashfiles;

import java.nio.charset.Charset;

/**
 * The <code>Hashable</code> interface should be implemented by any
 * class whose instances are intended to be hashed in string in some way.
 */
public interface Hashable {

    /**
     * Get hash of instance which class implements this interface
     * @param charset encoding of inputting data and output string
     * @return hash representation of instance which class implements this interface
     */
    public String hash(Charset charset);

    /**
     * Get name of hashing structure
     * @return name of hashing structure
     */
    public String getName();

}
