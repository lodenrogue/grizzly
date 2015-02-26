package com.lodenrogue.grizzly.http.request;

public abstract class Request {
	private String key;
	private RequestType type;

	public Request(String key, RequestType type) {
		this.key = key;
		this.type = type;
	}

	public String getKey() {
		return key;
	}

	public RequestType getRequestType() {
		return type;
	}
	
}
