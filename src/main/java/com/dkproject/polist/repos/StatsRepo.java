package com.dkproject.polist.repos;

import com.dkproject.polist.entities.Stats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface StatsRepo extends JpaRepository<Stats,Long> {

    @Query("select s from Stats s where s.group_id = ?1 and s.discipline_id = ?2")
    Optional<Stats> findAllByGroupIdAndDisciplineId(Long group_id, Long discipline_id);

    @Query("select s from Stats s where s.group_id = ?1")
    List<Stats> findAllByGroupId(Long group_id);


}
