package com.dkproject.polist.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PastStats {
    private Long group_id;
    private Long discipline_id;
    private int pastHours;
}
