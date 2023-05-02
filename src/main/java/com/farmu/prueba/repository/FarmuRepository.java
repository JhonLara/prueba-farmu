package com.farmu.prueba.repository;

import org.springframework.data.repository.CrudRepository;

import com.farmu.prueba.entity.URL;

public interface FarmuRepository extends CrudRepository<URL, Long> {
}