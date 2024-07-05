package com.dkproject.polist.services;

import com.dkproject.polist.dtos.ApiDto;
import com.dkproject.polist.dtos.PastStats;
import com.dkproject.polist.entities.*;
import com.dkproject.polist.repos.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StatsService {
    private final StatsRepo statsRepo;
    private final GroupRepo groupRepo;
    private final DisciplineRepo disciplineRepo;
    private final PareRepo pareRepo;
    private final TeacherRepo teacherRepo;

    public StatsService(StatsRepo statsRepo, GroupRepo groupRepo, DisciplineRepo disciplineRepo, PareRepo pareRepo, TeacherRepo teacherRepo) {
        this.statsRepo = statsRepo;
        this.groupRepo = groupRepo;
        this.disciplineRepo = disciplineRepo;
        this.pareRepo = pareRepo;
        this.teacherRepo = teacherRepo;
    }

    public ResponseEntity<?> getGroupStats(Long group_id, Long discipline_id){
        try {
            groupRepo.findById(group_id).orElseThrow(()->new RuntimeException("Группа не найдена"));
            disciplineRepo.findById(discipline_id).orElseThrow(()->new RuntimeException("Дисципилина не найдена"));
            Stats stats = statsRepo.findAllByGroupIdAndDisciplineId(group_id,discipline_id).orElseThrow(()->new RuntimeException("Статистика не найдена"));
            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> addPlanedStats(Long group_id, Long discipline_id, int hours) {
        try {
            if(groupRepo.findById(group_id).isPresent() && disciplineRepo.findById(discipline_id).isPresent()){
                Stats stats = statsRepo.findAllByGroupIdAndDisciplineId(group_id, discipline_id).orElseThrow(() -> new RuntimeException("Статистика не найдена"));
                stats.setPlanedHours(hours);
                statsRepo.save(stats);
                return ResponseEntity.ok(new ApiDto("Статистика сохранена"));
            }
            return new ResponseEntity<>(new ApiDto("Группа или дисциплина указаны не правильно"),HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            Stats stats = new Stats();
            stats.setDiscipline_id(discipline_id);
            stats.setGroup_id(group_id);
            stats.setPlanedHours(hours);
            statsRepo.save(stats);
            return new ResponseEntity<>(new ApiDto("Статистика создана"),HttpStatus.BAD_REQUEST);
        }
    }

    public List<Stats> getPlanedStatsOnGroup(Long group_id){
        return statsRepo.findAllByGroupId(group_id);
    }

    public List<PastStats> getPastStats(Long group_id){
        List<Discipline> disciplines = disciplineRepo.findAll();
        List<PastStats> pastStats = new ArrayList<>();
        disciplines.forEach(discipline -> {
            List<Pare> pares = pareRepo.findPastPareByGroupIdAndDisciplineId(group_id, discipline.getId());
            pastStats.add(
                    new PastStats(
                            group_id,
                            discipline.getId(),
                            pares.size() * 2
                    )
            );
        });
        return pastStats;

    }

//    public ResponseEntity<?> getCountOnPareByTeacher(Long teacher_id){
//        Teacher teacher = teacherRepo.findById(teacher_id).orElseThrow(()->new RuntimeException("Преподаватель не найден"));
//        ArrayList<Pare> pares = new ArrayList<>(teacher.getPares());
//        pares.stream().filter(pare -> {
//
//        }).collect(Collectors.toList());
//
//    }
}