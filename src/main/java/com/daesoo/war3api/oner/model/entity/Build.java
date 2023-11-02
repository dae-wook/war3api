package com.daesoo.war3api.oner.model.entity;


import com.daesoo.war3api.entity.Timestamp;
import com.daesoo.war3api.mrpg.model.dto.ItemSettingRequestDto;
import com.daesoo.war3api.mrpg.model.entity.ItemSettingShare;
import com.daesoo.war3api.oner.model.dto.BuildRequestDto;
import com.daesoo.war3api.user.model.entity.User;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Build extends Timestamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    private String title;

    @Column(length = 1000)
    private String equipments;
    @Column(length = 1000)
    private String sailors;
    @Column(length = 1000)
    private String ship;
    private String colleagues;

    private String characterName;

    public static Build create(BuildRequestDto dto, User user) {
        return Build.builder()
                .user(user)
                .title(dto.getTitle())
                .equipments(dto.getEquipment())
                .sailors(dto.getSailor())
                .ship(dto.getShip())
                .characterName(dto.getCharacterName())
                .colleagues(dto.getColleague())
                .build();
    }

    public void update(BuildRequestDto dto) {
        this.title = dto.getTitle();
        this.equipments = dto.getEquipment();
        this.sailors = dto.getSailor();
        this.colleagues = dto.getColleague();
        this.ship = dto.getShip();
        this.characterName = dto.getCharacterName();
    }
//    private LocalDateTime regDt;


}
