package org.lanit.internship.finaltask.model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PersonRepo extends JpaRepository<Person, Long> {
    Optional<Person> findById(Long id);
    Optional<Long> findAllById();
}
