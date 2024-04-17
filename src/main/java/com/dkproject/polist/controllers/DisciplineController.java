package com.dkproject.polist.controllers;

import com.dkproject.polist.repos.DisciplineRepo;
import com.dkproject.polist.services.DisciplineService;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/discipline")
public class DisciplineController {
    private final DisciplineService disciplineService;

    public DisciplineController(DisciplineService disciplineService) {
        this.disciplineService = disciplineService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addDiscipline(@Param("name") String name){
        return disciplineService.addDisciplineService(name);
    }
}
