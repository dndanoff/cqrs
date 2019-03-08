package com.danoff.cqrs.util;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class XmlSerializer {
	private XmlSerializer() {
	}

	public static String serialize(Object obj) {
		if(obj == null) {
			return null;
		}
		
		String xml = null;
		try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
				XMLEncoder encoder = new XMLEncoder(baos);) {
			encoder.writeObject(obj);
			xml = new String(baos.toByteArray());
		} catch (IOException e) {
			e.printStackTrace();
		}

		return xml;
	}

	public static <T> T deserialize(String xml, Class<T> type) {
		if(xml == null) {
			return null;
		}
		
		Object obj = null;
		try(ByteArrayInputStream bios = new ByteArrayInputStream(xml.getBytes());
				XMLDecoder decoder = new XMLDecoder(bios);){
			obj = decoder.readObject();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return type.isInstance(obj) ? type.cast(obj) : null;
	}
}
