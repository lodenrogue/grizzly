package com.lodenrogue.grizzly.http.request;

public class ObjectRequest extends Request {
	private Request child;

	public ObjectRequest(String key, Request child) {
		super(key, RequestType.OBJECT);
		this.child = child;
	}

	public Request getChild() {
		return child;
	}

}
