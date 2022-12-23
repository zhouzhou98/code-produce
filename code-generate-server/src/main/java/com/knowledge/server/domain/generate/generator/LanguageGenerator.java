package com.knowledge.server.domain.generate.generator;

import java.io.FileNotFoundException;

public interface LanguageGenerator {
    String download() throws FileNotFoundException;
}
