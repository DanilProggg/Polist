package com.dkproject.polist.services;

import com.dkproject.polist.dtos.PareDto;
import com.dkproject.polist.entities.*;
import com.dkproject.polist.repos.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public class PareService {
    private final PareRepo pareRepo;
    private final UserRepo userRepo;
    private final DisciplineRepo disciplineRepo;

    private final TeacherRepo teacherRepo;
    private final ClassroomRepo classroomRepo;
    private final GroupRepo groupRepo;

    public PareService(PareRepo pareRepo, UserRepo userRepo, DisciplineRepo disciplineRepo, TeacherRepo teacherRepo, ClassroomRepo classroomRepo, GroupRepo groupRepo) {
        this.pareRepo = pareRepo;
        this.userRepo = userRepo;
        this.disciplineRepo = disciplineRepo;
        this.teacherRepo = teacherRepo;
        this.classroomRepo = classroomRepo;
        this.groupRepo = groupRepo;
    }

    public ResponseEntity<?> addPareService(Principal principal, PareDto pareDto) {
        try {
            User user = userRepo.findByName(principal.getName()).orElseThrow(() -> new UsernameNotFoundException("Пользователь не авторизирован"));
            Discipline discipline = disciplineRepo.findById(pareDto.getGroup_id()).orElseThrow(()->new RuntimeException("Группа не найдена"));
            Classroom classroom = classroomRepo.findById(pareDto.getClassroom_id()).orElseThrow(()->new RuntimeException("Аудитория не найдена"));
            Teacher teacher = teacherRepo.findById(pareDto.getTeacher_id()).orElseThrow(()->new RuntimeException("Преподаватель не найден"));
            Group group = groupRepo.findById(pareDto.getGroup_id()).orElseThrow(()->new RuntimeException("Группа не найден"));
            Pare pare = new Pare();
            pare.setNumber(pare.getNumber());
            pare.setDate(pareDto.getDate());
            pare.setGroup_id(group.getId());
            pare.setDiscipline_id(discipline.getId());
            pare.setTeacher_id(teacher.getId());
            pare.setClassroom_id(classroom.getId());
            pareRepo.save(pare);
            return ResponseEntity.ok(pare);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    /*public ResponseEntity<?> getDayService(Long group_id, Long day){

    }*/
/*
    public List<Pare> getDayService(String group, Long date){
        return pareRepo.findUsingDay(group, date);
    }*/

}
