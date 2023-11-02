package com.daesoo.war3api.mrpg.model.entity;

import com.daesoo.war3api.mrpg.model.dto.MrpgMaterialRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Entity;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MrpgMaterial extends MrpgItem{
    private String type;

    public MrpgMaterial(MrpgMaterialRequestDto requestDto) {
        this.setName(requestDto.getName());
        this.setDropMonster(requestDto.getDropMonster());
        this.type = "material";
    }
}
