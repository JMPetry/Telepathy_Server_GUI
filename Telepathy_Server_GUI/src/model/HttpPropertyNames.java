package model;

public enum HttpPropertyNames {
	SEC_NUM("secNum"), PHONE_NAME("phoneName"), URL_TO_OPEN("urlToOpen");
	
	private final String value;

	HttpPropertyNames(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }
}
