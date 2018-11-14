package com.hb.wh.ts.zx.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hb.wh.ts.zx.domain.Airport;
import com.hb.wh.ts.zx.interfacelayer.kafka.Sender;
import com.hb.wh.ts.zx.service.AirportService;

@RestController
@RequestMapping(value="/airport")
public class AirportController {
	
	@Autowired
	private AirportService airportService;
	
	@RequestMapping(value="/demo")
	@Transactional
	public String test(@RequestParam String code) {
		
		String json= "{\"code\":{\"value\":"
				+ code+",\"priority\":60}}";
		
		airportService.updateAirport(json);
		//sender.send("test", "testing message");
		return null;
	}
    
	@RequestMapping(value="/all")
	public Map<String, Object> retrieveAll(){
		Map<String, Object> result = new HashMap<>();
		result.put("data", airportService.findAll());
		result.put("total", airportService.total());
		return result;
	}
	
}
