package com.dkproject.polist.repos;

import com.dkproject.polist.dtos.PastStats;
import com.dkproject.polist.entities.Pare;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface PareRepo extends JpaRepository<Pare, Long> {

    @Query("select p from Pare p where p.group_id = ?1 and p.date >= ?2 and p.date <= ?3 order by p.date, p.number")
    List<Pare> findWeekByGroupId(Long group_id, Date from, Date to);

    @Query("select p from Pare p where p.date >= ?1 and p.date <= ?2 order by p.date, p.number")
    List<Pare> findAllWeek(Date from, Date to);

    @Modifying
    @Query("delete from Pare p where p.date >= ?1 and p.date <= ?2")
    void deleteWeek(Date from, Date to);

    @Query("select p from Pare p where p.group_id = ?1 and p.date = ?2 and p.number = ?3 and p.subgroup = ?4")
    Optional<Pare> findByGroup_idAndDateAndNumberAndSubgroup(Long group_id, Date date, int number, int subgroup);

    @Query("select count(p) from Pare p where p.group_id = ?1")
    List<Pare> findCountByGroup_id(Long group_id);

    /*
    For Stats
     */
    @Query("select p from Pare p where p.group_id = ?1 and p.discipline_id = ?2 and p.date < CURRENT_DATE()")
    List<Pare> findPastPareByGroupIdAndDisciplineId(Long group_id, Long discipline_id);

    @Modifying
    @Query("delete Pare p where p.group_id = ?1 and p.date = ?2 and p.number = ?3 and p.subgroup = ?4")
    void deleteByGroup_idAndDateAndNumber(Long group_id, Date date, int number, int subgroup);
}
