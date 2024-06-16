package com.dkproject.polist.services;

import com.dkproject.polist.dtos.ApiDto;
import com.dkproject.polist.dtos.IdAndNumberClassroomDto;
import com.dkproject.polist.entities.Classroom;
import com.dkproject.polist.entities.User;
import com.dkproject.polist.repos.ClassroomRepo;
import com.dkproject.polist.repos.UserRepo;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
public class ClassroomService {
    private final ClassroomRepo classroomRepo;
    private final UserRepo userRepo;

    public ClassroomService(ClassroomRepo classroomRepo, UserRepo userRepo) {
        this.classroomRepo = classroomRepo;
        this.userRepo = userRepo;
    }

    public ResponseEntity<?> addClassroomService(String number){
        if(classroomRepo.findByNumber(number).isEmpty()){
            Classroom classroom = new Classroom();
            classroom.setNumber(number);
            classroomRepo.save(classroom);
            return ResponseEntity.ok(classroomRepo.findAll(Sort.by("number")));
        }
        return new ResponseEntity<>(new ApiDto("Не удалось добавить аудиторию"), HttpStatus.BAD_REQUEST);
    }

    public List<IdAndNumberClassroomDto> allClassroomsService(){
        List<Classroom> classroom = classroomRepo.findAll();
        List<IdAndNumberClassroomDto> idAndNumberClassroomDto = new ArrayList<>();
        classroom.forEach(classroom1 -> {
            idAndNumberClassroomDto.add(
                    new IdAndNumberClassroomDto(
                    classroom1.getId(),
                    classroom1.getNumber()
            ));
        });
        return idAndNumberClassroomDto;
    }

    public ResponseEntity<?> deleteClassroomService(Long id){
        try {
            classroomRepo.deleteById(id);
            return ResponseEntity.ok(classroomRepo.findAll(Sort.by("number")));
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiDto(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

}
