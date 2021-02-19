package utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {
	
	Properties prop = null;
	
 
	public  PropertiesUtil (String fileName) throws IOException {
		InputStream inputStream = null;
		try {
			
			inputStream = getClass().getClassLoader().getResourceAsStream(fileName);
			if (inputStream != null) {
				prop = new Properties();
				prop.load(inputStream);
				inputStream.close();
			} else {
				throw new FileNotFoundException("property file '" + fileName + "' not found in the classpath");
			}

			

		} catch (Exception e) {
			System.out.println("Exception: " + e);
		}
	}
	
	
	public String getValue(String propertyName) {
		if(this.prop == null) return null;
		return this.prop.getProperty(propertyName);
	}
	
	public static void main(String[] args) throws IOException {
		PropertiesUtil configProperties = new PropertiesUtil("C:\\Users\\thier\\Documents\\gitclones\\dgi_workspace\\jee\\sunualbum\\build\\classes\\utils\\config.properties");
    	String imageLocation = configProperties.getValue("location.album");
    	System.out.println(imageLocation);
	}
	
}
