package com.cibertec.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cibertec.entity.Alumno;

public interface ClienteRepository extends JpaRepository<Alumno, Integer> {

	

}
