package com.dkproject.polist.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PareDto {

    private Long group_id;
    private boolean sub;
    private Date date;

    private int number;
    private int subgroup;

    private Long discipline_id;
    private Long teacher_id;
    private Long classroom_id;
}
