package com.dkproject.polist.repos;

import com.dkproject.polist.entities.Discipline;
import com.dkproject.polist.entities.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DisciplineRepo extends JpaRepository<Discipline, Long> {
    Optional<Discipline> findByName(String name);

}
