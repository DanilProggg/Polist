package com.dkproject.polist.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;


@Entity
@Getter
@Setter
@NoArgsConstructor
public class Pare {
    //Добавить int subgroup; для показа подгруппы
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private boolean sub;
    private Long group_id;
    private int number;
    private int subgroup;
    private Date date;
    private Long discipline_id;
    private Long teacher_id;
    private Long classroom_id;
}
