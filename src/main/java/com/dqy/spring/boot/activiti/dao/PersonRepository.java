package com.dqy.spring.boot.activiti.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dqy.spring.boot.activiti.entity.Person;

public interface PersonRepository extends JpaRepository<Person, Long>  {
	   Person findByUsername(String username);
}
