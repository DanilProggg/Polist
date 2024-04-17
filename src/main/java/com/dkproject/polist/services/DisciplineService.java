package com.dkproject.polist.services;

import com.dkproject.polist.entities.Discipline;
import com.dkproject.polist.repos.DisciplineRepo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class DisciplineService {
    private final DisciplineRepo disciplineRepo;

    public DisciplineService(DisciplineRepo disciplineRepo) {
        this.disciplineRepo = disciplineRepo;
    }

    public ResponseEntity<?> addDisciplineService(String name){
        if(disciplineRepo.findByName(name).isEmpty()){
            Discipline discipline = new Discipline();
            discipline.setName(name);
            disciplineRepo.save(discipline);
            return ResponseEntity.ok("Дисциплина добавленна успешно");
        }
        return new ResponseEntity<>("Не удалось добавить дисциплину", HttpStatus.BAD_REQUEST);
    }
}
