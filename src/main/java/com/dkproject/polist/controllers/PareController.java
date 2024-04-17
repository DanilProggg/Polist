package com.dkproject.polist.controllers;

import com.dkproject.polist.dtos.PareDto;
import com.dkproject.polist.services.PareService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/pare")
public class PareController {
    private final PareService pareService;

    public PareController(PareService pareService) {
        this.pareService = pareService;
    }

    @GetMapping("/all-list")
    private String getList(){
        return "hello";
    }

    /*@GetMapping("/day")
    private List<Pare> getDay(@RequestParam("group") String group, @RequestParam("date") Long date){
        return pareService.getDayService(group, date);
    }*/

    @PostMapping("/add")
    private ResponseEntity<?> addPare(Principal principal, @RequestBody PareDto pareDto){
        return pareService.addPareService(principal, pareDto);
    }
}
