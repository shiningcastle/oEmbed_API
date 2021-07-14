package com.example.demo.service;

import org.springframework.http.ResponseEntity;

import com.google.gson.JsonObject;

public interface OembedService {
	public ResponseEntity<JsonObject> getOembed(String inputUrl) throws Exception;
}