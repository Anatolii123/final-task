package org.lanit.internship.finaltask.model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PersonRepo extends JpaRepository<Person, String> {
    Optional<Person> findById(String id);
}
