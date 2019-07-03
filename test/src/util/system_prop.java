package util;

import java.util.Enumeration;
import java.util.Properties;

public class system_prop {

	public static void main(String[] args) {
		Properties props = System.getProperties();
        for(Enumeration en = props.propertyNames(); en.hasMoreElements();) {
            String key = (String)en.nextElement();
            String value = props.getProperty(key);
            System.out.println(key + "=" + value);
        }

	}

}
