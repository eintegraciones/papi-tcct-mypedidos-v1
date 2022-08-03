package com.ntt.transformer;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class AvroSchema {

	protected static void writeSchema(String dataSchema, String nameSchema) {
		
		String pathNameSchema = "tmp/schemas/" + nameSchema;
        try {
        	File file = new File(pathNameSchema);
			FileUtils.writeByteArrayToFile(file, dataSchema.getBytes());
        } catch(IOException e) {
            e.printStackTrace();
        } 
	}
	
}
