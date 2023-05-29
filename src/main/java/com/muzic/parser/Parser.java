package com.muzic.parser;

import com.muzic.model.RekordboxXML;

import java.io.File;

public interface Parser {

    RekordboxXML parseXmlFile(File file);
}
