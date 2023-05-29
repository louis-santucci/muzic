package com.muzic.parser;

import com.muzic.model.RekordboxXML;

import java.io.File;
import java.util.concurrent.CompletableFuture;

public interface Parser {

    RekordboxXML parseXmlFile(File file);
}
