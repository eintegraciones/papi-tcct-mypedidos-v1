package com.ntt.transformer;

import org.apache.avro.Schema;

public interface AvroTraductor {

	public Schema traduct(Object data);
	
}
