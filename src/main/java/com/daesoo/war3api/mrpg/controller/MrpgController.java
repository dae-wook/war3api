package com.daesoo.war3api.mrpg.controller;

import com.daesoo.war3api.dto.ResponseDto;
import com.daesoo.war3api.mrpg.model.dto.MrpgEquipmentDto;
import com.daesoo.war3api.mrpg.model.dto.MrpgMaterialRequestDto;
import com.daesoo.war3api.mrpg.model.entity.MrpgEquipment;
import com.daesoo.war3api.mrpg.service.MrpgApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.TreeSet;

@Controller
@RequiredArgsConstructor
@RequestMapping("/mrpg/admin")
@ApiIgnore
public class MrpgController {

    private final MrpgApiService mrpgApiService;

    @GetMapping("/")
    public String index() {

        return "mrpg/index";
    }

    @ResponseBody
    @GetMapping("/dropMonsters")
    public TreeSet<String> getDropMonster() {
        return mrpgApiService.getDropMonsters();
    }

    @ResponseBody
    @PostMapping("/equipment")
    public ResponseDto<String> createEquipment(@RequestBody MrpgEquipmentDto mrpgEquipmentDto) {
        return mrpgApiService.createEquipment(mrpgEquipmentDto);
    }

    @ResponseBody
    @PostMapping("/material")
    public ResponseDto<String> createMaterial(@RequestBody MrpgMaterialRequestDto mrpgMaterialRequestDto) {
        return mrpgApiService.createMaterial(mrpgMaterialRequestDto);
    }

    @ResponseBody
    @GetMapping("/equipment")
    public MrpgEquipmentDto parseString(String parse) {
        MrpgEquipmentDto dto = new MrpgEquipmentDto();
        return dto.parse(parse);
    }



}
