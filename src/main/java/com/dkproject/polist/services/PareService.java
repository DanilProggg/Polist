package com.dkproject.polist.services;

import com.dkproject.polist.dtos.ApiDto;
import com.dkproject.polist.dtos.PareDto;
import com.dkproject.polist.entities.Pare;
import com.dkproject.polist.repos.PareRepo;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Service
public class PareService {

    private final PareRepo pareRepo;

    public PareService(PareRepo pareRepo) {
        this.pareRepo = pareRepo;
    }

    public List<Pare> getParesByGroupService(Long id, Date from, Date to){
        return pareRepo.findWeekByGroupId(id,from,to);
    }

    public List<Pare> getParesFromToService(java.sql.Date from, java.sql.Date to){
        return pareRepo.findAllWeek(from,to);
    }


    @Transactional
    public ResponseEntity<?> uploadParesService(List<PareDto> paresDto, Date from, Date to){
        List<Pare> pares = new ArrayList<>();
        paresDto.forEach(pareDto -> {
            Pare pare = new Pare();
            pare.setDate(pareDto.getDate());
            pare.setNumber(pareDto.getNumber());
            pare.setClassroom_id(pareDto.getClassroom_id());
            pare.setDiscipline_id(pareDto.getDiscipline_id());
            pare.setTeacher_id(pareDto.getTeacher_id());
            pare.setGroup_id(pareDto.getGroup_id());
            pare.setSubgroup(pareDto.getSubgroup());
            pares.add(pare);
        });
        pareRepo.deleteWeek(from, to);
        pareRepo.saveAll(pares);
        return ResponseEntity.ok(pareRepo.findAllWeek(from, to));
    }

}
