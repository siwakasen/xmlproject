
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
 * @author riksi
 */

 //Project ini untuk menyimpan history data yang sedikit tanpa menggunakan database
  //data tersimpan di dalam file berekstensi .xml

public class XMLProject {
    public static void main(String[] args)
    throws ParserConfigurationException, TransformerException {
       //auto generate path menggunakan nama 
       //cara kerja generate path ini bukan mencari file .xmlnya,
       // akan tetapi dgn mencari path dari project ini kemudian menambahkan nama file .xml dibelakangnya
        Path path = Paths.get("history.xml");

        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

        // root elements
        Document doc = docBuilder.newDocument();
        Element rootElement = doc.createElement("history");
        doc.appendChild(rootElement);

        // rewrite nama elemen
        Element kendaraan = doc.createElement("Mobil");
        rootElement.appendChild(kendaraan);

        //menambahkan data id di dalam elemen
        Element id = doc.createElement("id");
        id.setTextContent("MBL-1");
        kendaraan.appendChild(id);

        //rewrite nama elemen kedua
        Element kendaraan2 = doc.createElement("Motor");
        rootElement.appendChild(kendaraan2);

        //menambahkan data id di dalam elemen kedua
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

        //mengambil data dari file .xml
        File file = new File(path.toAbsolutePath().toString());
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        try{
            //parsing dari dalam file.xml kemudian disimpan pada object data
            Document data = db.parse(file);

            //print data id dari history
            System.out.println(getHistoryID(data,"Mobil")); 
            System.out.println(getHistoryID(data,"Motor")); 
        }catch(Exception e){
            System.out.println(e.toString());
        }

    }

    // menulis ulang data di dalam .xml
    private static void writeXml(Document doc,OutputStream output)
        throws TransformerException {

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(output);

        transformer.transform(source, result);

    }
    //fungsi mengambil data di dalam object data
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




//kamu menemukan pesan gabut dari orang gabut
