package model;

/**
 * Enum of the names of the properties which are used in http requests
 * @author Jean
 */
public enum HttpPropertyNames {
	SEC_NUM("secNum"), PHONE_NAME("phoneName"), URL_TO_OPEN("urlToOpen"), FILE_NAME("fileName");
	
	private final String value;

	HttpPropertyNames(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }
}
