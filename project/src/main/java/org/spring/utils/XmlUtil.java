package org.spring.utils;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class XmlUtil {

	public static <T> T parseXml(String xmlString, Class<T> clazz) throws IOException {
		XmlMapper xmlMapper = new XmlMapper();

		//System.out.println("parseXml xmlString: " + xmlString);

		T node = xmlMapper.readValue(xmlString.getBytes(), clazz);

		//System.out.println("XmlUtil JsonNode: " + node);

		ObjectMapper jsonMapper = new ObjectMapper();
		String json = jsonMapper.writeValueAsString(node);

		//System.out.println("XmlUtil json: " + json);

		T result = JsonUtil.parseJson(json, clazz);

		//System.out.println("XmlUtil: " + clazz);

		return result;

	}
}
