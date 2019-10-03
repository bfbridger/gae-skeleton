package com.bigfishgames.gaeskeleton.sample;

import com.bigfishgames.gaeskeleton.sample.messages.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1/sample")
public class SampleResource {
	private static final Logger log = LoggerFactory.getLogger(SampleResource.class);

	private SampleService sampleService;

	@Autowired
	public SampleResource(SampleService sampleService) {
		this.sampleService = sampleService;
	}

	@GetMapping("/get/{id}")
	public SampleGetResponse sampleGet(@PathVariable Long id) {
		SampleGetResponse response = new SampleGetResponse();
		response.setMessage("sampleGet: " + id);

		return response;
	}

	@PostMapping("/post")
	public SamplePostResponse samplePost(@RequestBody SamplePostRequest request) {
		return new SamplePostResponse(request.getRequestInt(), request.getRequestString(), request.getRequestFloat());
	}

	@GetMapping("/getmemcachevalue/{key}")
	public MemcacheGetResponse getMemcacheValue(@PathVariable String key) {
		MemcacheGetResponse response = new MemcacheGetResponse();
		response.setKey(key);
		response.setValue(this.sampleService.getValue(key));
		return response;
	}

	@PostMapping("/setmemcachevalue")
	public void setMemcacheValue(@RequestBody MemcacheSetRequest request) {
		this.sampleService.setValue(request.getKey(), request.getValue());
	}
}
