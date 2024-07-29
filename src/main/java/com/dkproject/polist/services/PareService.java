package com.dkproject.polist.services;

import com.dkproject.polist.dtos.PareDto;
import com.dkproject.polist.entities.Classroom;
import com.dkproject.polist.entities.Discipline;
import com.dkproject.polist.entities.Pare;
import com.dkproject.polist.entities.Teacher;
import com.dkproject.polist.repos.ClassroomRepo;
import com.dkproject.polist.repos.DisciplineRepo;
import com.dkproject.polist.repos.PareRepo;
import com.dkproject.polist.repos.TeacherRepo;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.*;

@Service
public class PareService {

    private final PareRepo pareRepo;
    private final DisciplineRepo disciplineRepo;
    private final TeacherRepo teacherRepo;
    private final ClassroomRepo classroomRepo;

    public PareService(PareRepo pareRepo, DisciplineRepo disciplineRepo, TeacherRepo teacherRepo, ClassroomRepo classroomRepo) {
        this.pareRepo = pareRepo;
        this.disciplineRepo = disciplineRepo;
        this.teacherRepo = teacherRepo;
        this.classroomRepo = classroomRepo;
    }

    public List<Pare> getParesByGroupService(Long id, Date from, Date to){
        return pareRepo.findWeekByGroupId(id,from,to);
    }

    public ResponseEntity<?> getParesByGroupAndDayWithNames(Long id, Date date){
        List<Pare> pares = pareRepo.findParesByGroupIdAndDay(id, date);
        List<Map<String,String>> array = new LinkedList<>();
        pares.forEach(pare -> {
            Map<String,String> map = new HashMap<>();
            map.put("id",pare.getId().toString());
            map.put("sub", String.valueOf(pare.isSub()));
            map.put("group_id",pare.getGroup_id().toString());
            map.put("number",String.valueOf(pare.getNumber()));
            map.put("subgroup",String.valueOf(pare.getSubgroup()));
            map.put("date",pare.getDate().toString());
            Discipline discipline = disciplineRepo.findById(pare.getDiscipline_id()).orElseThrow(()->new RuntimeException("Дисциплина не найдена"));
            map.put("discipline",discipline.getName());
            Teacher teacher = teacherRepo.findById(pare.getTeacher_id()).orElseThrow(()->new RuntimeException("Преподаватель не найден"));
            map.put("teacher",teacher.getName());
            Classroom classroom = classroomRepo.findById(pare.getClassroom_id()).orElseThrow(()->new RuntimeException("Аудитория не найдена"));
            map.put("classroom",classroom.getNumber());
            array.add(map);
        });
        return ResponseEntity.ok(array);
    }

    public List<Pare> getParesFromToService(java.sql.Date from, java.sql.Date to){
        return pareRepo.findAllWeek(from,to);
    }


    @Transactional
    public ResponseEntity<?> uploadParesService(List<PareDto> paresDto, Date from, Date to){
        List<Pare> pares = new ArrayList<>();
        paresDto.forEach(pareDto -> {
            Pare pare = new Pare();
            pare.setDate(pareDto.getDate());
            pare.setNumber(pareDto.getNumber());
            pare.setClassroom_id(pareDto.getClassroom_id());
            pare.setDiscipline_id(pareDto.getDiscipline_id());
            pare.setTeacher_id(pareDto.getTeacher_id());
            pare.setGroup_id(pareDto.getGroup_id());
            pare.setSubgroup(pareDto.getSubgroup());
            pare.setSub(pareDto.isSub());
            pares.add(pare);
        });
        pareRepo.deleteWeek(from, to);
        pareRepo.saveAll(pares);
        return ResponseEntity.ok(pareRepo.findAllWeek(from, to));
    }

    @Transactional
    public ResponseEntity<?> uploadParesByPare(List<PareDto> paresDto, Date from, Date to){
        try {
            paresDto.forEach(pareDto -> {
                if(pareDto.getDiscipline_id().equals(0L) ||
                   pareDto.getTeacher_id().equals(0L) ||
                   pareDto.getClassroom_id().equals(0L)){
                   if(pareRepo.findByGroup_idAndDateAndNumberAndSubgroup(pareDto.getGroup_id(), new Date(pareDto.getDate().getTime()),pareDto.getNumber(), pareDto.getSubgroup()).isPresent()){
                        pareRepo.deleteByGroup_idAndDateAndNumber(pareDto.getGroup_id(), new Date(pareDto.getDate().getTime()),pareDto.getNumber(), pareDto.getSubgroup());
                    }
                }else{
                    pareRepo.deleteByGroup_idAndDateAndNumber(pareDto.getGroup_id(), new Date(pareDto.getDate().getTime()),pareDto.getNumber(), pareDto.getSubgroup());
                    Pare pare = new Pare();
                    pare.setClassroom_id(pareDto.getClassroom_id());
                    pare.setDiscipline_id(pareDto.getDiscipline_id());
                    pare.setNumber(pareDto.getNumber());
                    pare.setTeacher_id(pareDto.getTeacher_id());
                    pare.setSub(pareDto.isSub());
                    pare.setSubgroup(pareDto.getSubgroup());
                    pare.setGroup_id(pareDto.getGroup_id());
                    pare.setDate(pareDto.getDate());
                    pareRepo.save(pare);
                }
            });
            return new ResponseEntity<>(pareRepo.findAllWeek(from, to),HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
