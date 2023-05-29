package com.muzic.parser;

import com.muzic.model.RekordboxXML;
import com.muzic.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;

@Component
public class XmlParser implements Parser {
    private final EntityService entityService;
    private final SAXParser saxParser = SAXParserFactory.newInstance().newSAXParser();

    @Autowired
    public XmlParser(EntityService entityService) throws ParserConfigurationException, SAXException {
        this.entityService = entityService;
    }


    @Override
    public RekordboxXML parseXmlFile(File file, boolean persist) {
        try {
            RekordboxXMLHandler rekordboxXMLHandler = new RekordboxXMLHandler(persist, entityService);
            this.saxParser.parse(file, rekordboxXMLHandler);
            return rekordboxXMLHandler.getRekordboxXML();
        } catch (IOException | SAXException e) {
            return null;
        }
    }
}
