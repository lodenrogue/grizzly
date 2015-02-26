package com.lodenrogue.grizzly.http.request;

import java.util.ArrayList;
import java.util.List;

public class ResponseRequest {
	List<Request> requests;

	public ResponseRequest() {
		requests = new ArrayList<Request>();
	}

	public void addRequest(Request request) {
		requests.add(request);
	}

	public void addAllRequests(Request... requests) {
		for (Request r : requests) {
			this.requests.add(r);
		}
	}

	public List<Request> getRequests() {
		return requests;
	}
}
