package com.tydic.traffic.tafa.utils.mail;

public enum MailType {
	
	TXT("text/plain"),
	
	HTML("text/html");
	
	private String value;
	
	private MailType(String v){
		value = v;
	}
	
	public String value(){
		return value.toLowerCase();
	}
	
	public static MailType fromValue(String v) {
		MailType type = null;
        for (MailType c: MailType.values()) {
            if (c.value.equals(v.toLowerCase())) {
                type = c;
                break;
            }
        }
        return type;
    }

}
