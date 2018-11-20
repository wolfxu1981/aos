package com.hb.wh.ts.zx.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import com.hb.wh.ts.zx.domain.listeners.AirportListener;

@Entity
@Table(name="t_basic_airport")
@EntityListeners(AirportListener.class)
@DynamicUpdate
public class Airport implements Serializable{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="t_id")
	private Long id;
	
	@Column(name="a_code")
	private String code;
	@Column(name="iata")
	private String iata;
	@Column(name="icao")
	private String icao;
	@Column(name="a_name")
	private String name;
	@Column(name="a_alias")
	private String alias;
	@Column(name="a_city")
	private String city;
	@Column(name="a_country")
	private String country;
	@Column(name="a_country_type")
	private String countrytype;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getIata() {
		return iata;
	}

	public void setIata(String iata) {
		this.iata = iata;
	}

	public String getIcao() {
		return icao;
	}

	public void setIcao(String icao) {
		this.icao = icao;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCountrytype() {
		if(countrytype!=null) {
			switch (countrytype) {
			case "D":
				return "国内";
			case "I":
				return "国际";
			case "M":
				return "混合";
			default:
				break;
			}
		}
		return countrytype;
	}

	public void setCountrytype(String countrytype) {
		this.countrytype = countrytype;
	}
	
	
	
}


