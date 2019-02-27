package com.example.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.City;

@Repository("cityRepository")
public interface CityRepository extends JpaRepository<City, String> {

}
