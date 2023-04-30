package org.example;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class XML {
    boolean load;
    String loadFile;
    String loadFormat;

    boolean save;
    String saveFile;
    String saveFormat;

    boolean log;
    String logFile;

    public XML (File file) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(file);

        Element root = document.getDocumentElement();
        Element loadElement = (Element) root.getElementsByTagName("load").item(0);
        Element saveElement = (Element) root.getElementsByTagName("save").item(0);
        Element logElement = (Element) root.getElementsByTagName("log").item(0);

        load = Boolean.parseBoolean(loadElement.getElementsByTagName("enabled").item(0).getTextContent());
        loadFile = loadElement.getElementsByTagName("fileName").item(0).getTextContent();
        loadFormat = loadElement.getElementsByTagName("format").item(0).getTextContent();

        save = Boolean.parseBoolean(saveElement.getElementsByTagName("enabled").item(0).getTextContent());
        saveFile = saveElement.getElementsByTagName("fileName").item(0).getTextContent();
        saveFormat = saveElement.getElementsByTagName("format").item(0).getTextContent();

        log = Boolean.parseBoolean(logElement.getElementsByTagName("enabled").item(0).getTextContent());
        logFile = logElement.getElementsByTagName("fileName").item(0).getTextContent();
    }
}
