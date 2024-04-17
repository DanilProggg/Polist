package com.dkproject.polist.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Pare {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long group_id;
    private int number;
    private Long date;
    private Long discipline_id;
    private Long teacher_id;
    private Long classroom_id;
}
