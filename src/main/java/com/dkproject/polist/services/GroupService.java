package com.dkproject.polist.services;

import com.dkproject.polist.dtos.ApiDto;
import com.dkproject.polist.entities.Group;
import com.dkproject.polist.entities.User;
import com.dkproject.polist.repos.GroupRepo;
import com.dkproject.polist.repos.UserRepo;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            return ResponseEntity.ok(groupRepo.findAll(Sort.by("name")));
        }

        return new ResponseEntity<>(new ApiDto("Не удалось создать группу"),HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<?> deleteGroupService(Long id){
        try {
            groupRepo.deleteById(id);
            return ResponseEntity.ok(groupRepo.findAll(Sort.by("name")));
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiDto(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> updateGroup(Long id, Long date){
        try {
            Group group = groupRepo.findById(id).orElseThrow(()->new RuntimeException("Группа не найдена"));
            group.setStartDate(date);
            groupRepo.save(group);
            return ResponseEntity.ok(groupRepo.findAll(Sort.by("name")));
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiDto(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    public List<Group> allGroups(){
        return groupRepo.findAll(Sort.by("name"));
    }


    public ResponseEntity<?> getGroupIdByName(String name){
        Group group = groupRepo.findByName(name).orElseThrow(()->new RuntimeException("Группа не найдена"));
        Map map = new HashMap();
        map.put("id", group.getId());
        return ResponseEntity.ok(map);
    }
}
