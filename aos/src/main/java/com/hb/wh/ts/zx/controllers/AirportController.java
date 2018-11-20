package com.hb.wh.ts.zx.controllers;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hb.wh.ts.zx.domain.Airport;
import com.hb.wh.ts.zx.service.AirportService;

@RestController
@RequestMapping(value = "/airport")
public class AirportController {

	@Autowired
	private AirportService airportService;

	@RequestMapping(value = "/demo")
	@Transactional
	public String test(@RequestParam String code) {

		String json = "{\"code\":{\"value\":" + code + ",\"priority\":60}}";

		airportService.updateAirport(json);
		// sender.send("test", "testing message");
		return null;
	}

	@RequestMapping(value = "/all")
	public Map<String, Object> retrieveAll() {
		Map<String, Object> result = new HashMap<>();
		result.put("data", airportService.findAll());
		result.put("total", airportService.total());
		return result;
	}
	
	@RequestMapping(value = "/save")
	public Boolean save(@RequestBody String request) {
		Boolean result = true;
		Airport airport = new Airport();
		JSONObject requestobj = new JSONObject(new JSONObject(request).get("airport").toString());
		//如果有id
		if(requestobj.has("id")) {
			airport = airportService.findById(Long.valueOf(requestobj.getInt("id")));
		}
		for(String key:requestobj.keySet()) {
			Field field;
			try {
				field = Airport.class.getDeclaredField(key);
				if(field!=null) {
					field.setAccessible(true);
				    if(key.equals("id")) {
				    	field.set(airport, Long.valueOf(String.valueOf(requestobj.get(key))));
				    }else {
				    	field.set(airport, requestobj.get(key));
				    }
				}		
			} catch (NoSuchFieldException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		result = airportService.updateAirport(airport);
		return result;
	}

	@RequestMapping(value = "/all/condition")
	public Map<String, Object> retrieveAll(@RequestBody String request) {
		Airport example = new Airport();
		Map<String, Object> result = new HashMap<>();
		if (request != null) {
			JSONObject requestobj = new JSONObject(request);
			if (requestobj != null) {
				Object airport = requestobj.get("airport");
				if(!airport.equals(null)) {
					JSONObject airport_ = new JSONObject(airport.toString());
					for(String key:airport_.keySet()) {
						for (Field field : Airport.class.getDeclaredFields()) {
							if (field.getName().equals(key)) {
								field.setAccessible(true);
								try {
									field.set(example, airport_.get(key));
									break;
								} catch (IllegalArgumentException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (IllegalAccessException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}
					}
				}
 				int start = requestobj.getInt("start");
				int size = requestobj.getInt("size");

				Pageable pageable = new PageRequest(start, size);

				Page<Airport> airports = airportService.findAll(example, pageable);
				result.put("data", airports.getContent());
				result.put("total", airports.getTotalElements());
			}
		}
		return result;
	}
}
