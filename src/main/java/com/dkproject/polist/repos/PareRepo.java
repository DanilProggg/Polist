package com.dkproject.polist.repos;

import com.dkproject.polist.entities.Pare;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PareRepo extends JpaRepository<Pare, Long> {

}
