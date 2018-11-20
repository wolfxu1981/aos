package com.hb.wh.ts.zx.domain.listeners;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Properties;

import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hb.wh.ts.zx.domain.Airport;
import com.hb.wh.ts.zx.interfacelayer.kafka.Sender;
import com.hb.wh.ts.zx.utils.BeanUtil;


public class AirportListener {

	private static Airport original;
	private Properties limits;
	
	@PrePersist
	@PreUpdate
	public void airportPreUpdate(Airport airport) {

		InputStream in = AirportListener.class.getClassLoader().getResourceAsStream("airport_columns.properties");
		limits = new Properties();
		try {
			limits.load(in);
			// 更新前需要对比
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@PostPersist
	@PostUpdate
	public void airportPostUpdate(Airport airport) {
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
		HashMap<String, Object> result = new HashMap<>();
//		for (Field field : Airport.class.getDeclaredFields()) {
//			field.setAccessible(true);
//			try {
//				// 对于空值需要进行处理
//				// 开始为空，新值不为空
//				if (field!=null&&field.get(original) == null && (field.get(airport) == null)) {
//					result.put(field.getName(), field.get(original));
//				} else if (field.get(original) == null && (field.get(airport) != null)
//						|| (field.get(original) != null && (field.get(airport) == null))
//						||(!field.get(original).equals(field.get(airport)))) {
//					// 这里拼装相关的更新消息
//					HashMap<String, Object> changed = new HashMap<>();
//					changed.put("oldvalue", field.get(original));
//					changed.put("value", field.get(airport));
//					result.put(field.getName(), changed);
//					System.out.println(
//							field.getName() + " changed from:" + field.get(original) + " to:" + field.get(airport));
//				}else {
//					result.put(field.getName(), field.get(original));
//				}
//			} catch (IllegalArgumentException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (IllegalAccessException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//
//		}
//		
//		try {
//			HashMap<String, Object> output= new HashMap<>();
//			output.put("airport", result);
//			//拼装JSON格式的数据
//			//BeanUtil.getBean(Sender.class).send("test", new ObjectMapper().writeValueAsString(output));
//			System.out.println(new ObjectMapper().writeValueAsString(output));
//			//拼装相应的XML
//		} catch (JsonProcessingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

	@PostLoad
	public void postLoad(Airport airport) {
		original = new Airport();
		for (Field field : Airport.class.getDeclaredFields()) {
			field.setAccessible(true);
			try {
				field.set(original, field.get(airport));
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println(airport.getCode());
	}

}
