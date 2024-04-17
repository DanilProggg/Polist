package com.dkproject.polist.services;

import com.dkproject.polist.entities.Teacher;
import com.dkproject.polist.repos.TeacherRepo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class TeacherService {
    private final TeacherRepo teacherRepo;

    public TeacherService(TeacherRepo teacherRepo) {
        this.teacherRepo = teacherRepo;
    }

    public ResponseEntity<?> addTeacherService(String name){
        if (teacherRepo.findByName(name).isEmpty()){
            Teacher teacher = new Teacher();
            teacher.setName(name);
            teacherRepo.save(teacher);
            return ResponseEntity.ok("Преподаватель добавлен успешно");
        }
        return new ResponseEntity<>("Не удалось добавить преподавателя", HttpStatus.BAD_REQUEST);
    }
}
