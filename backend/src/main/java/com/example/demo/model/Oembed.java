package com.example.demo.model;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;

public abstract class Oembed  {
	abstract public HttpResponse getOembedInfo(String url) throws ClientProtocolException;
}