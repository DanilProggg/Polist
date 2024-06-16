package com.dkproject.polist.controllers;

import com.dkproject.polist.dtos.IdAndNumberClassroomDto;
import com.dkproject.polist.services.ClassroomService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/v1/classroom")
public class ClassroomController {
    private final ClassroomService classroomService;

    public ClassroomController(ClassroomService classroomService) {
        this.classroomService = classroomService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addClassroom(@RequestParam String number){
        return classroomService.addClassroomService(number);
    }

    @PostMapping("/delete")
    public ResponseEntity<?> deleteClassroom(@RequestParam Long id){
        return classroomService.deleteClassroomService(id);
    }

    @GetMapping("/all")
    public List<IdAndNumberClassroomDto> getClassrooms(){
        return classroomService.allClassroomsService();
    }
}
