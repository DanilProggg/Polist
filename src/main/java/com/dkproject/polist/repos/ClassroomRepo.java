package com.dkproject.polist.repos;

import com.dkproject.polist.entities.Classroom;
import com.dkproject.polist.entities.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClassroomRepo extends JpaRepository<Classroom, Long> {
    Optional<Classroom> findByNumber(String number);
}
