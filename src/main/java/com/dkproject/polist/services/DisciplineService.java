package com.dkproject.polist.services;

import com.dkproject.polist.dtos.ApiDto;
import com.dkproject.polist.entities.Discipline;
import com.dkproject.polist.repos.DisciplineRepo;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

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
            return ResponseEntity.ok(disciplineRepo.findAll(Sort.by("name")));
        }
        return new ResponseEntity<>(new ApiDto("Не удалось добавить дисциплину"), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<?> deleteDisciplineService(Long id){
        try {
            disciplineRepo.deleteById(id);
            return ResponseEntity.ok(disciplineRepo.findAll(Sort.by("name")));
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiDto(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    public List<Discipline> allDisciplines(){
        return disciplineRepo.findAll(Sort.by("name"));
    }
}
