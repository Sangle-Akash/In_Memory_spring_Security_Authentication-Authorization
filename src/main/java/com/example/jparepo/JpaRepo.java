package com.example.jparepo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.Person;

public interface JpaRepo extends JpaRepository<Person, Long> {

}
