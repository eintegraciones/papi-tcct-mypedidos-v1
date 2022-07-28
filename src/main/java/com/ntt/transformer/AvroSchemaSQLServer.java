package com.ntt.transformer;

public class AvroSchemaSQLServer extends AvroSchema implements AvroTraductor {

	@Override
	public void fileSchema(Object data, String tableName) {
		String schema = "{\"type\" : \"record\", \"namespace\" : \"com.nttdata.bean\", \"name\" : \"" + tableName + "\", \"fields\" : [";
		/*for(String key : map.keySet()) {
			if(map.get(key) instanceof Double) {
				schema = schema + "{\"name\" : \"" + key + "\", \"type\" : [\"null\", \"double\"] },";
			} else if(map.get(key) instanceof Boolean) {
				schema = schema + "{\"name\" : \"" + key + "\", \"type\" : [\"null\", \"boolean\"] },";
			} else if(map.get(key) instanceof Integer) {
				schema = schema + "{\"name\" : \"" + key + "\", \"type\" : [\"null\", \"int\"] },";
			} else if(map.get(key) instanceof Float) {
				schema = schema + "{\"name\" : \"" + key + "\", \"type\" : [\"null\", \"float\"] },";
			} else if(map.get(key) instanceof Long) {
				schema = schema + "{\"name\" : \"" + key + "\", \"type\" : [\"null\", \"long\"] },";
			} else if(map.get(key) instanceof String) {
				schema = schema + "{\"name\" : \"" + key + "\", \"type\" : [\"null\", \"string\"] },";
			} else {
				schema = schema + "{\"name\" : \"" + key + "\", \"type\" : [\"null\", \"string\"] },";
			}
		}*/
		schema = schema.substring(0, schema.length() - 1);
		schema = schema + "]}";
		
		
		
		writeSchema(schema, tableName + ".schame");
	}



}
