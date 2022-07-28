package com.ntt.transformer;

import java.io.File;
import java.io.IOException;

import org.apache.avro.generic.GenericData;
import org.apache.commons.io.FileUtils;
import org.apache.parquet.hadoop.ParquetReader;

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
}
