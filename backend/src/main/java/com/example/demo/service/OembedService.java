package com.example.demo.service;

import org.springframework.http.ResponseEntity;

import com.google.gson.JsonObject;

public interface OembedService {
	public JsonObject getOembed(String inputUrl);
}