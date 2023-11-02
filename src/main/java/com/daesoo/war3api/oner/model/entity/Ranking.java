package com.daesoo.war3api.oner.model.entity;

import com.daesoo.war3api.entity.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Ranking extends Timestamp {

    @Id
    private String nickName;
    private String saveDate;
    private String name;
    private int exp;
    private int lv;
    private int bounty;
    private String sailors;
    private String colleagues;
    private String ship;
    private int end;
    @ManyToOne
    private Hero hero;


}
