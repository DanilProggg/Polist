package com.dkproject.polist.controllers;

import com.dkproject.polist.dtos.PareDto;
import com.dkproject.polist.entities.Pare;
import com.dkproject.polist.services.ExcelService;
import com.dkproject.polist.services.PareService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1/pare")
public class PareController {
    private final PareService pareService;
    private final ExcelService excelService;

    public PareController(PareService pareService, ExcelService excelService) {
        this.pareService = pareService;
        this.excelService = excelService;
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

    @GetMapping("/bygroup-and-date")
    public ResponseEntity<?> getParesByGroupId(
            @RequestParam Long id,
            @RequestParam Date date
    ){
        return pareService.getParesByGroupAndDayWithNames(id, date);
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

    @GetMapping("/report")
    @ResponseBody
    public ResponseEntity<ByteArrayResource> getReport(
            @RequestParam Date to,
            @RequestParam Date from,
            @RequestParam Long group_id
            ) throws IOException {
       return excelService.writeIntoExcel(from, to, group_id);
    }
}
