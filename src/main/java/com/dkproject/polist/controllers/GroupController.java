package com.dkproject.polist.controllers;

import com.dkproject.polist.entities.Group;
import com.dkproject.polist.repos.GroupRepo;
import com.dkproject.polist.services.GroupService;
import org.apache.coyote.Response;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/group")
public class GroupController {
    private final GroupService groupService;
    private final GroupRepo groupRepo;

    public GroupController(GroupService groupService, GroupRepo groupRepo) {
        this.groupService = groupService;
        this.groupRepo = groupRepo;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addGroup(@RequestParam String name){
        return groupService.addGroupService(name);
    }


    @GetMapping("/get")
    public String getGroup(@Param("id") Long id){
        Group group = groupRepo.findById(id).orElseThrow(
                ()->new RuntimeException("Группа не найдена")
        );
        return group.getName();
    }

    @PostMapping("/delete")
    public ResponseEntity<?> deleteGroup(@RequestParam Long id){
        return groupService.deleteGroupService(id);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateGroup(@RequestParam Long id, @RequestParam Long date){
        return groupService.updateGroup(id, date);
    }

    @GetMapping("/all")
    public List<Group> getGroups(){
        return  groupService.allGroups();
    }
}
