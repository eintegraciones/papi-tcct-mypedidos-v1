package com.ntt.parquet;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.avro.Schema.Field;
import org.apache.avro.generic.GenericData;
import org.apache.commons.io.FileUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.parquet.avro.AvroParquetReader;
import org.apache.parquet.hadoop.ParquetReader;
import org.apache.parquet.hadoop.util.HadoopInputFile;

public class ParquetToJsonConvertor {

	public static List<Map<String, Object>> readParquetFile(String filename, byte[] data) {
		List<Map<String, Object>> resp = new ArrayList<>();
		
		String pathName = "tmp/" + filename;
		ParquetReader<GenericData.Record> reader = null;
        try {
        	// Guardamos los bytes en un fichero temporal
        	File file = new File(pathName);
			FileUtils.writeByteArrayToFile(file, data);
			
			HadoopInputFile hif = HadoopInputFile.fromPath(new Path(pathName), new Configuration());
        	reader = AvroParquetReader
                    .<GenericData.Record>builder(hif)
                    .build();
        	
            GenericData.Record record;
            List<Field> fields = null;
            while ((record = reader.read()) != null) {
            	if(fields == null) {
            		fields = record.getSchema().getFields();
            	}
            	Map<String, Object> kv = new LinkedHashMap<>();
                for(Field f : fields) {
                	kv.put(f.name(), record.get(f.name()));
                }
                resp.add(kv);
            }
            
            file.delete();
        } catch(IOException e) {
            e.printStackTrace();
        } finally {
            if(reader != null) {
                try {
                	reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        
        return resp;
    }
	
}
