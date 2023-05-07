/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package xmlproject;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.*;
/**
 *
 * @author riksi
 */
public class XMLProject {
    public static void main(String[] args)
    throws ParserConfigurationException, TransformerException {
        Path path = Paths.get("history.xml");
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

        // root elements
        Document doc = docBuilder.newDocument();
        Element rootElement = doc.createElement("history");
        doc.appendChild(rootElement);

        // kendaraan jenis motor
        Element kendaraan = doc.createElement("Mobil");
        rootElement.appendChild(kendaraan);

        Element id = doc.createElement("id");
        id.setTextContent("MBL-1");
        kendaraan.appendChild(id);

        //kendaraan jenis mobil
        Element kendaraan2 = doc.createElement("Motor");
        rootElement.appendChild(kendaraan2);

        Element id2 = doc.createElement("id");
        id2.setTextContent("MTR-1");
        kendaraan2.appendChild(id2);

        

        try (FileOutputStream output =
            new FileOutputStream(path.toAbsolutePath().toString())) {
            writeXml(doc, output);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // print XML to system console
        writeXml(doc, System.out);
        //print history ID Kendaraan
        File file = new File(path.toAbsolutePath().toString());
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        try{
            Document data = db.parse(file);
            System.out.println(getHistoryID(data,"Mobil")); 
            System.out.println(getHistoryID(data,"Motor")); 
        }catch(Exception e){
            System.out.println(e.toString());
        }

    }

    // write doc to output stream
    private static void writeXml(Document doc,OutputStream output)
        throws TransformerException {

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(output);

        transformer.transform(source, result);

    }
    private static String getHistoryID(Document doc, String jenis){
        NodeList historyNodes = doc.getElementsByTagName(jenis);
        for(int i=0; i<historyNodes.getLength(); i++){
            Node historyNode = historyNodes.item(i);
            if(historyNode.getNodeType() == Node.ELEMENT_NODE){
                Element historyElement = (Element) historyNode;
                String historyId = historyElement.getElementsByTagName("id").item(i).getTextContent();
                return historyId;
            }
        }
        return "";
    }
}
