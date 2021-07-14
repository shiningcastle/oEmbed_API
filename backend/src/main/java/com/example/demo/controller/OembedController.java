package com.example.demo.controller;

import javax.validation.constraints.NotBlank;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.OembedService;
import com.google.gson.JsonObject;

@CrossOrigin(origins = "http://localhost:8081") 
@RestController
public class OembedController {
	private final OembedService oembedService;

	public OembedController(OembedService oembedService) {
		this.oembedService = oembedService;
	}
	
	@GetMapping("/oembed")
	public ResponseEntity<JsonObject> oembed(@RequestParam @NotBlank String url) throws Exception {
		return oembedService.getOembed(url);
	}
}