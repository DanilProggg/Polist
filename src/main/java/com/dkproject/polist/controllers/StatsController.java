package com.dkproject.polist.controllers;

import com.dkproject.polist.dtos.PastStats;
import com.dkproject.polist.entities.Stats;
import com.dkproject.polist.services.StatsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/stats")
public class StatsController {
    private final StatsService statsService;

    public StatsController(StatsService statsService) {
        this.statsService = statsService;
    }

    @GetMapping("/planed")
    public List<Stats> getPlanedStatsOnGroup(
            @RequestParam Long group
    ) {
        return statsService.getPlanedStatsOnGroup(group);
    }



    @PostMapping("/add")
    public ResponseEntity<?> addPlanedStats(
            @RequestParam int hours,
            @RequestParam Long group,
            @RequestParam Long discipline
    ) {
        return statsService.addPlanedStats(group,discipline, hours);
    }

    @GetMapping("/past")
    public List<PastStats> getPastStats(
            @RequestParam Long group
    ){
        return statsService.getPastStats(group);
    }
}
