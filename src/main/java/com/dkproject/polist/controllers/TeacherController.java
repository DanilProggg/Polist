package com.dkproject.polist.controllers;

import com.dkproject.polist.entities.Teacher;
import com.dkproject.polist.repos.PareRepo;
import com.dkproject.polist.services.TeacherService;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/teacher")
public class TeacherController {
    private final TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addTeacher(Principal principal, @Param("name") String name){
        return teacherService.addTeacherService(principal, name);
    }

    @PostMapping("/delete")
    public ResponseEntity<?> deleteTeacher(@RequestParam Long id){
        return teacherService.deleteTeacherService(id);
    }

    @GetMapping("/all")
    public List<Teacher> getAll(){
        return teacherService.allTeachers();
    }
}
