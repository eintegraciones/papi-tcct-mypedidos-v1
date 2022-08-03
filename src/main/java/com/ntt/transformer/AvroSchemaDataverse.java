package com.ntt.transformer;

import java.io.StringReader;

import javax.management.modelmbean.XMLParseException;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class AvroSchemaDataverse extends AvroSchema {

	public static String generateSchema(String data, String tableName) throws XMLParseException {
		StringBuilder schema = new StringBuilder();
		Document docXML = convertStringToXMLDocument(data);
		docXML.getDocumentElement().normalize();
		Element eElement = getNodeTable(docXML, tableName);
		
		schema.append("{\"type\" : \"record\", \"namespace\" : \"com.nttdata.bean\", \"name\" : \"" + tableName + "\", \"fields\" : [");
		NodeList nListProperty =  eElement.getElementsByTagName("Property");
		for (int temp = 0; temp < nListProperty.getLength(); temp++) {
			Node nNode = nListProperty.item(temp);

			if (temp > 0) {
				schema.append(",");
			}
			
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElementProperty = (Element)nNode;
				
				String name = eElementProperty.getAttribute("Name"); 
				schema.append("{\"name\":\"").append(name).append("\",");
				
				String type = eElementProperty.getAttribute("Type");
				switch (type) {
				case "Edm.String":
					schema.append("\"type\":[\"null\",\"string\"]");
					break;
				case "Edm.Double":
					schema.append("\"type\":[\"null\",\"double\"]");
					break;
				case "Edm.Boolean":
					schema.append("\"type\":[\"null\",\"boolean\"]");
					break;
				case "Edm.Int32":
					schema.append("\"type\":[\"null\",\"int\"]");
					break;
				case "Edm.Float":
				case "Edm.Decimal":
					schema.append("\"type\":[\"null\",\"float\"]");
					break;
				case "Edm.Int64":
					schema.append("\"type\":[\"null\",\"long\"]");
					break;
				default:
					schema.append("\"type\":[\"null\",\"string\"]");
					break;
				}
				schema.append("}");
			}
		}
		schema.append("]}");
		
		return schema.toString();
	}

	public static Document convertStringToXMLDocument(String xmlString) {
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
	
	protected static Element getNodeTable(Document docXML, String tableName) throws XMLParseException {
		Element eElementTable = null;
		Element eElementRoot = docXML.getDocumentElement();
		XPath xPath = XPathFactory.newInstance().newXPath();
		try {
			System.out.println(String.format("OriginalTableName: %s", tableName));
			String rawRealTable = xPath.evaluate("//EntitySet[@Name=\"" + tableName + "\"]/@EntityType", eElementRoot);
			System.out.println(String.format("RawRealTable: %s", rawRealTable));
			String[] splitArr = rawRealTable.split("\\.");
			String realTable = splitArr[splitArr.length - 1];
			System.out.println(String.format("Extracted table name: %s", realTable));
			eElementTable = (Element) xPath.evaluate("//EntityType[@Name=\"" + realTable + "\"]", eElementRoot, XPathConstants.NODE);
		} catch (XPathExpressionException e) {
			System.err.println(e.getMessage());
		}
        if(eElementTable == null) {
        	throw new XMLParseException(String.format("No se encontró la tabla \"(%s)\" en el documento XML de Dataverse", tableName));
        }
		return eElementTable;
	}

}
