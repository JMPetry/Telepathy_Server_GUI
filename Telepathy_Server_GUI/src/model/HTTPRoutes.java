package model;

public enum HTTPRoutes {
	OPENURL("/openTab"), START("/start"), SEND_FILE("/sendFile");
	
	private final String value;

    HTTPRoutes(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }
}
