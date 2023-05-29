package com.muzic.parser;

import com.muzic.model.RekordboxXML;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;

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
