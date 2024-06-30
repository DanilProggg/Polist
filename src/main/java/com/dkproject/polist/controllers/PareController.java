package com.dkproject.polist.controllers;

import com.dkproject.polist.dtos.PareDto;
import com.dkproject.polist.entities.Pare;
import com.dkproject.polist.services.PareService;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1/pare")
public class PareController {
    private final PareService pareService;

    public PareController(PareService pareService) {
        this.pareService = pareService;
    }

    @GetMapping("/week")
    public List<Pare> getParesOnWeek(
            @RequestParam Date from,
            @RequestParam Date to
    ){
        return pareService.getParesFromToService(from, to);
    }

    @GetMapping("/bygroup")
    public List<Pare> getParesByGroupId(
            @RequestParam Long id,
            @RequestParam Date from,
            @RequestParam Date to
        ){
        return pareService.getParesByGroupService(id,from, to);
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadPares(
            @RequestBody List<PareDto> pares,
            @RequestParam Date from,
            @RequestParam Date to
        ){
        return pareService.uploadParesService(pares, from, to);
    }

    @PostMapping("/upload/v2")
    public ResponseEntity<?> uploadParesV2(
            @RequestBody List<PareDto> pares,
            @RequestParam Date from,
            @RequestParam Date to
    ){
        return pareService.uploadParesByPare(pares, from, to);
    }
}
