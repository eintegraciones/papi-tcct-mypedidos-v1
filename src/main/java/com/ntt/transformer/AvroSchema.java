package com.ntt.transformer;

import java.io.File;
import java.io.IOException;

import org.apache.avro.generic.GenericData;
import org.apache.commons.io.FileUtils;
import org.apache.parquet.hadoop.ParquetReader;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import org.xml.sax.InputSource;

public class AvroSchema {

	protected void writeSchema(String dataSchema, String nameSchema) {
		
		String pathNameSchema = "tmp/" + nameSchema;
		ParquetReader<GenericData.Record> reader = null;
        try {
        	// Guardamos los bytes en un fichero temporal
        	File file = new File(pathNameSchema);
			FileUtils.writeByteArrayToFile(file, dataSchema.getBytes());
        } catch(IOException e) {
            e.printStackTrace();
        } 
	}
	
	
	protected Document convertStringToXMLDocument(String xmlString) {
		Document doc = null;
		
		//Parser that produces DOM object trees from XML content
	    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	     
	    //API to obtain DOM Document instance
	    DocumentBuilder builder = null;
	    try {
	      //Create DocumentBuilder with default configuration
	      builder = factory.newDocumentBuilder();
	       
	      //Parse the content to Document object
	     doc = builder.parse(new InputSource(new StringReader(xmlString)));
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
	    return doc;
	}
	
	protected Element getNodeTable(Document docXML, String tableName) {
		Element eElementTable = null;
		Element eElementRoot = docXML.getDocumentElement();
		NodeList nList = eElementRoot.getElementsByTagName("EntityType");
        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);

            //System.out.println("\nCurrent Element :" + nNode.getNodeName() + " Node.ELEMENT_NODE::"+  Node.ELEMENT_NODE);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
            	Element eElement = (Element)nNode;
            	String name = eElement.getAttribute("Name");
            	//System.out.println(name);
            	if (tableName.equals(name)) {
            		eElementTable = eElement;
            	}
            }

        }
		return eElementTable;
	}
}
