package com.dkproject.polist.services;

import com.dkproject.polist.entities.Classroom;
import com.dkproject.polist.repos.ClassroomRepo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ClassroomService {
    private final ClassroomRepo classroomRepo;

    public ClassroomService(ClassroomRepo classroomRepo) {
        this.classroomRepo = classroomRepo;
    }

    public ResponseEntity<?> addClassroomService(String number){
        if(classroomRepo.findByNumber(number).isEmpty()){
            Classroom classroom = new Classroom();
            classroom.setNumber(number);
            classroomRepo.save(classroom);
            return ResponseEntity.ok("Аудитория добавленна успешно");
        }
        return new ResponseEntity<>("Не удалось добавить аудиторию", HttpStatus.BAD_REQUEST);
    }


}
