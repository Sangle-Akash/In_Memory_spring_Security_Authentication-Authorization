package com.example.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Person;
import com.example.jparepo.JpaRepo;

import jakarta.websocket.server.PathParam;

@RestController
public class Jpacontroller {
	@Autowired
	JpaRepo repo;

	@PostMapping("/entity-save")
	public ResponseEntity<Map<String, Person>> saveEntity(@RequestBody Person person) {
		Map<String, Person> map = new HashMap<>();

		if (!(person == null)) {
			repo.save(person);
			Person saved = repo.findById(person.getId()).orElseThrow(NoSuchElementException::new);
			map.put("data save successfully", saved);
		} else {
			map.put("data is not saved", null);
		}

		return new ResponseEntity<Map<String, Person>>(map, HttpStatus.OK);

	}

	@GetMapping("/find-by-id/{id}")
	public ResponseEntity<Person> getThePerson(@PathParam(value = "id") Long id) {
		// Fetch the person by ID
		Optional<Person> person = repo.findById(id);

		// Check if the person is present
		if (person.isPresent()) {
			return new ResponseEntity<>(person.get(), HttpStatus.OK); // Return 200 OK with the person object
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Return 404 Not Found if not found
		}
	}

	@GetMapping("/get-all-recoreds")
	public ResponseEntity<List<Person>> getAllTheRecordFromTable() {

		List<Person> list = repo.findAll();

		return new ResponseEntity<List<Person>>(list, HttpStatus.OK);

	}
	@DeleteMapping("/delet-person/{id}")
	public ResponseEntity<String> personDelete(@PathParam(value = "id") Long id){
		Optional<Person> person = repo.findById(id);
		if(person.isPresent()) {
			repo.deleteById(id);	
			return new ResponseEntity<String>("Data is  deleted successfully",HttpStatus.OK) ;
		}
		else {
			return new ResponseEntity<String>("Data is not deleted successfully",HttpStatus.NOT_FOUND) ;
		}
	}
}
