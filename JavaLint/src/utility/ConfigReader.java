package utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import structure.Rule;

public class ConfigReader {

	public final static String PROPERTY_FILE_NAME = "config.properties";
	private static Properties prop = new Properties();
	private static InputStream input = null;
	private static String filePropertyPath;
	private final static String DEFAULT_PATH = "PATH";

	
	// Return the name of the property file which will be read to set up rules
	public static String getConfigFilePath() {
		if (OSUtils.isWindows()) {
			filePropertyPath = System.getProperty("user.dir") + "\\src\\" + PROPERTY_FILE_NAME;
		}
		if (OSUtils.isMac()) {
			filePropertyPath = System.getProperty("user.dir") + "/src/" + PROPERTY_FILE_NAME;
		}
		return filePropertyPath;
	}

	public static Map<Rule, Boolean> getAuthorization() {

		try {
			Map<Rule, Boolean> propertyMap = new HashMap<Rule, Boolean>();
			input = new FileInputStream(getConfigFilePath());

			// load our property file
			prop.load(input);

			// get values and return map
			propertyMap.put(Rule.LINE_SIZE, readValueProp("LINE_SIZE"));
			propertyMap.put(Rule.STRING_INSTANTIATION, readValueProp("STRING_INSTANTIATION"));
			propertyMap.put(Rule.CONSTANT_UPPERCASE, readValueProp("CONSTANT_UPPERCASE"));
			propertyMap.put(Rule.CLASS_NAME_FORMAT, readValueProp("CLASS_NAME_FORMAT"));
			propertyMap.put(Rule.PARAMS_FUNCTION, readValueProp("PARAMS_FUNCTION"));
			propertyMap.put(Rule.CHECK_NULL_INPUT, readValueProp("CHECK_NULL_INPUT"));
			propertyMap.put(Rule.FINAL_DECLARATION_MISSING, readValueProp("FINAL_DECLARATION_MISSING"));
			propertyMap.put(Rule.NESTED_SPACES, readValueProp("NESTED_SPACES"));

			return propertyMap;
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	// Return the path which is used to find java files recursively
	public static String getProjectPropertyFolderPath() {
		try {
			input = new FileInputStream(getConfigFilePath());
			prop.load(input);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return prop.getProperty(DEFAULT_PATH);
	}

	
	private static Boolean readValueProp(String propertyName) {
		return (prop.getProperty(propertyName).equals("true") ? true : false);
	}

}