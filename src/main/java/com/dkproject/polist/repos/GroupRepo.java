package com.dkproject.polist.repos;

import com.dkproject.polist.entities.Group;
import com.dkproject.polist.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GroupRepo extends JpaRepository<Group, Long> {
    Optional<Group> findByName(String name);
}
