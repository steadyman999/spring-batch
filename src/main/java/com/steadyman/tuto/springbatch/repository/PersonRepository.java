package com.steadyman.tuto.springbatch.repository;

import com.steadyman.tuto.springbatch.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
