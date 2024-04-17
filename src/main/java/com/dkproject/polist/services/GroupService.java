package com.dkproject.polist.services;

import com.dkproject.polist.entities.Group;
import com.dkproject.polist.repos.GroupRepo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class GroupService {
    private final GroupRepo groupRepo;

    public GroupService(GroupRepo groupRepo) {
        this.groupRepo = groupRepo;
    }

    public ResponseEntity<?> addGroupService(String name){
        if(groupRepo.findByName(name).isEmpty()){
            Group group = new Group();
            group.setName(name);
            group.setStartDate(new Date().getTime());
            groupRepo.save(group);
            return ResponseEntity.ok("Группа добавленна успешно");
        }
        return new ResponseEntity<>("Не удалось добавить группу",HttpStatus.BAD_REQUEST);
    }
}
