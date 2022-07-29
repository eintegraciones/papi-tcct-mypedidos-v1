package com.ntt.transformer;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class AvroSchemaDataverse extends AvroSchema {

	
	public AvroSchemaDataverse() {
		//System.out.println("Creo instancia");
	}

	public void fileSchema(String data, String tableName) {
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
					schema.append("\"type\":\"[\"null\",\"string\"]");
					break;
				case "Edm.Double":
					schema.append("\"type\":\"[\"null\",\"double\"]");
					break;
				case "Edm.Boolean":
					schema.append("\"type\":\"[\"null\",\"boolean\"]");
					break;
				case "Edm.Int32":
					schema.append("\"type\":\"[\"null\",\"int\"]");
					break;
				case "Edm.Float":
				case "Edm.Decimal":
					schema.append("\"type\":\"[\"null\",\"float\"]");
					break;
				case "Edm.Int64":
					schema.append("\"type\":\"[\"null\",\"long\"]");
					break;
				default:
					schema.append("\"type\":\"[\"null\",\"string\"]");
					break;
				}
				schema.append("}");
			}
		}
		schema.append("]}");

		System.out.println(schema.toString());
		
		writeSchema(schema.toString(), tableName + ".schame");
	}

	



}
