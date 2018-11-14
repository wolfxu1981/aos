package com.hb.wh.ts.zx.dao;

import java.util.Optional;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import com.hb.wh.ts.zx.domain.Airport;

@Repository
public interface AirportDao extends JpaRepository<Airport, Long>{
	
    @Lock(LockModeType.PESSIMISTIC_WRITE)
	Optional<Airport> findById(Long id);
    
}
