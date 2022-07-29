package com.ntt.transformer;

import java.util.Map;

public class AvroSchemaSQLServer extends AvroSchema {

	public static void fileSchema(Map<String, String> data, String tableName) {
		StringBuilder schema = new StringBuilder();
		schema.append("{\"type\" : \"record\", \"namespace\" : \"com.nttdata.bean\", \"name\" : \"" + tableName + "\", \"fields\" : [");
		int temp = 0;
		for(String key : data.keySet()) {
			if (temp > 0) {
				schema.append(",");
			}
			
			schema.append("{\"name\":\"").append(key).append("\",");

			String type = data.get(key);
			switch (type) {
				case "nvarchar":
					schema.append("\"type\":\"[\"null\",\"string\"]");
					break;
				// No parece que haya un equivalente a double en SQL Server
				//case "":
					//schema.append("\"type\":\"[\"null\",\"double\"]");
					//break;
				case "bit":
					schema.append("\"type\":\"[\"null\",\"boolean\"]");
					break;
				case "int":
					schema.append("\"type\":\"[\"null\",\"int\"]");
					break;
				case "float":
				case "decimal":
					schema.append("\"type\":\"[\"null\",\"float\"]");
					break;
				case "bigint":
					schema.append("\"type\":\"[\"null\",\"long\"]");
					break;
				default:
					schema.append("\"type\":\"[\"null\",\"string\"]");
					break;
			}
			schema.append("}");
			
			temp++;
		}
		schema.append("]}");
		
		writeSchema(schema.toString(), tableName + ".schema");
	}



}
