package com.muzic.parser;

import com.muzic.model.RekordboxXML;
import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;

@Component
public class XmlParser implements Parser {
    private final SAXParser saxParser = SAXParserFactory.newInstance().newSAXParser();

    public XmlParser() throws ParserConfigurationException, SAXException {
    }


    @Override

    public RekordboxXML parseXmlFile(File file) {
        try {
            RekordboxXMLHandler rekordboxXMLHandler = new RekordboxXMLHandler();
            this.saxParser.parse(file, rekordboxXMLHandler);
            return rekordboxXMLHandler.getRekordboxXML();
        } catch (IOException | SAXException e) {
            return null;
        }
    }
}
