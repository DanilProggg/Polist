package com.dkproject.polist.controllers;

import com.dkproject.polist.services.ClassroomService;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/")
public class ClassroomController {
    private final ClassroomService classroomService;

    public ClassroomController(ClassroomService classroomService) {
        this.classroomService = classroomService;
    }

    public ResponseEntity<?> addClassroom(@Param("name") String name){
        return classroomService.addClassroomService(name);
    }
}
