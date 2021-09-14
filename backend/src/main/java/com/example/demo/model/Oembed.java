package com.example.demo.model;

import org.apache.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Oembed  {
	final Logger logger = LoggerFactory.getLogger(this.getClass());
	abstract public HttpResponse getOembedInfo(String url);
}