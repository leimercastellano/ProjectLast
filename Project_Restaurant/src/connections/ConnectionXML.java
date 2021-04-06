package connections;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;

public class ConnectionXML implements Connection{

    public Document doc;
    String path = "src/resources/Restaurant.xml";

    @Override
    public void connect() {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            factory.setValidating(true);
            factory.setIgnoringElementContentWhitespace(true);
            File xml = new File(path);
            doc = builder.parse(xml);
        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() {
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(path));
            StreamResult console = new StreamResult(System.out);
            transformer.transform(source,result);
            transformer.transform(source,console);
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

    public Document getDoc() {
        return doc;
    }
}