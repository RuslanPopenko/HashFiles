package com.rubiconproject.hashfiles;

import java.nio.charset.Charset;

public interface Hashable {

    public String hash(Charset charset);

    public String getName();

}
