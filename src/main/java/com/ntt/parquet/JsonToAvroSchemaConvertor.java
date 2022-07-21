package com.ntt.parquet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.parquet.avro.AvroParquetWriter;
import org.apache.parquet.hadoop.ParquetWriter;
import org.apache.parquet.hadoop.metadata.CompressionCodecName;

public class JsonToAvroSchemaConvertor {
	
	private static Map<String, Object> extractFieldMapFromJson(List<Map<String, Object>> json) {
		Map<String, Object> resp = new LinkedHashMap<String, Object>();
		int keySize = json.get(0).keySet().size();
		int count = 0;
		Iterator<Map<String, Object>> it = json.iterator();
		
		while(it.hasNext() && count < keySize) {
			Map<String, Object> record = it.next();
			for(String key : record.keySet()) {
				if(!resp.containsKey(key) && record.get(key) != null && !record.get(key).equals("null")) {
					resp.put(key, record.get(key));
					count = count++;
				} else if(!it.hasNext() && !resp.containsKey(key)) {
					resp.put(key, record.get(key));
				}
			}
		}
		return resp;
	}
	
	public static Schema toAvroSchema(String tableName, List<Map<String, Object>> json) {
		Map<String, Object> map = extractFieldMapFromJson(json);
		
		String schema = "{\"type\" : \"record\", \"namespace\" : \"com.nttdata.bean\", \"name\" : \"" + tableName + "\", \"fields\" : [";
		for(String key : map.keySet()) {
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
		}
		schema = schema.substring(0, schema.length() - 1);
		schema = schema + "]}";
		Schema SCHEMA = new Schema.Parser().parse(schema);
		System.out.println("PARSED AVRO SCHEMA: " + SCHEMA);
		return SCHEMA;
	}

	public static void toParquet(String tmpPath, List<Map<String, Object>> data, Schema schema) {
		try(ParquetWriter<GenericData.Record> writer = AvroParquetWriter.<GenericData.Record>builder(new Path(tmpPath))
				.withRowGroupSize(256 * 1024 * 1024).withPageSize(1024 * 1024)
				.withConf(new Configuration())
				.withCompressionCodec(CompressionCodecName.SNAPPY)
				.withSchema(schema)
				.withValidation(false)
				.withDictionaryEncoding(false)
				.build()) {
			
			List<GenericData.Record> recordList = new ArrayList<GenericData.Record>();
			data.forEach(d -> {
					GenericData.Record record = new GenericData.Record(schema);
					d.forEach((K, V) -> record.put(K, V));
					recordList.add(record);
				});
			
			for(GenericData.Record r : recordList) {
				writer.write(r);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static byte[] getBytesOfJsonAsParquet(String tableName, List<Map<String, Object>> data) {
		String tmpPath = "tmp/" + tableName + System.currentTimeMillis() + ".parquet";
		System.out.println("JSON INPUT: " + data.toString().substring(0,500));
		Schema avroSchema = toAvroSchema(tableName, data);
		toParquet(tmpPath, data, avroSchema);
		byte[] resp = null;
		File file = new File(tmpPath);
		try {
			FileInputStream in = new FileInputStream(file);
			resp = new byte[(int)file.length()];
			in.read(resp);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// Eliminar el fichero temporal tras leer los bytes
			file.delete();
		}
		return resp;
	}

}
