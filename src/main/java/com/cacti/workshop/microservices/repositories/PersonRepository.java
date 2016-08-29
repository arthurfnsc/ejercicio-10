package com.cacti.workshop.microservices.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cacti.workshop.microservices.model.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {

}
