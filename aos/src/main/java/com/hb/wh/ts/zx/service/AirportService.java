package com.hb.wh.ts.zx.service;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hb.wh.ts.zx.dao.AirportDao;
import com.hb.wh.ts.zx.domain.Airport;

@Service
public class AirportService {
	
	@Autowired
	private AirportDao airportDao;
	
	private Properties pros;
	
	private void ini() {
		try {
			pros = PropertiesLoaderUtils.loadAllProperties("airport_columns.properties", AirportService.class.getClassLoader());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	//外部系统通过XML或者JSON方式进行封装处理的
	public boolean updateAirport(String data) {
		ini();
		Optional<Airport> airport = airportDao.findById(1L);
		if(!pros.isEmpty()) {
			//这里根据实际的需要和处理属性的优先级的字段
			try {
				//如果是JSON结构的数据
				HashMap<String, Object> tmp = new ObjectMapper().readValue(data, HashMap.class);
				for(String key:pros.stringPropertyNames()) {
					//如果有这个值就需要判断优先级
					if(tmp.containsKey(key)) {
						//如果优先级高的就set值，否则就不处理
						HashMap<String, Object> tmp_ = (HashMap<String, Object>) tmp.get(key);
						if(tmp_.containsKey("priority")) {
							int priority = Integer.parseInt(tmp_.get("priority").toString());
							if(priority>Integer.parseInt(pros.getProperty(key))){
								try {
									Field field = Airport.class.getDeclaredField(key);
									field.setAccessible(true);
									field.set(airport.get(), tmp_.get("value"));
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
								}
							}
						}
					}
				}
				if(airport.isPresent()) {
					//airport.get().setCode("NKG");
					airportDao.save(airport.get());
				}
			} catch (JsonParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return true;
	}
	
	//内部系统本身，默认权限是最高的，不需要进行优先级的校验
	public boolean updateAirport(Airport airport) {
		try {
			airportDao.saveAndFlush(airport);
			return true;
		}catch(Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}
	
	public List<Airport> findAll(){
		return airportDao.findAll();
	}
	
	public Page<Airport> findAll(Airport airport,Pageable pageable){
		
		ExampleMatcher matcher = ExampleMatcher.matching().withStringMatcher(StringMatcher.CONTAINING);
		
		return airportDao.findAll(Example.of(airport,matcher) ,pageable);
	}
	
	public Airport findById(Long id) {
		return airportDao.findById(id).get();
	}
	
	public Long total() {
		return airportDao.count();
	}

}
