package com.dkproject.polist.services;

import com.dkproject.polist.dtos.ApiDto;
import com.dkproject.polist.entities.Teacher;
import com.dkproject.polist.entities.User;
import com.dkproject.polist.repos.TeacherRepo;
import com.dkproject.polist.repos.UserRepo;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public class TeacherService {
    private final TeacherRepo teacherRepo;
    private final UserRepo userRepo;

    public TeacherService(TeacherRepo teacherRepo, UserRepo userRepo) {
        this.teacherRepo = teacherRepo;
        this.userRepo = userRepo;
    }

    public ResponseEntity<?> addTeacherService(Principal principal, String name){
        User user = userRepo.findByName(principal.getName()).orElseThrow(() -> new UsernameNotFoundException("Пользователь не авторизирован"));
        if (teacherRepo.findByName(name).isEmpty()){
            Teacher teacher = new Teacher();
            teacher.setName(name);
            teacherRepo.save(teacher);
            return ResponseEntity.ok(teacherRepo.findAll(Sort.by("name")));
        }
        return new ResponseEntity<>(new ApiDto("Не удалось добавить преподавателя"), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<?> deleteTeacherService(Long id){
        try {
            teacherRepo.deleteById(id);
            return ResponseEntity.ok(teacherRepo.findAll(Sort.by("name")));
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiDto(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    public List<Teacher> allTeachers(){
        return teacherRepo.findAll();
    }
}
