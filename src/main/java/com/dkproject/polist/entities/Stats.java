package com.dkproject.polist.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Stats {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long group_id;
    private Long discipline_id;
    private int planedHours;
}
