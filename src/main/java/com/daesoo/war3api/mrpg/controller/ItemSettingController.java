package com.daesoo.war3api.mrpg.controller;

import com.daesoo.war3api.dto.ResponseDto;
import com.daesoo.war3api.mrpg.model.dto.CountResponseDto;
import com.daesoo.war3api.mrpg.model.dto.ItemSettingRequestDto;
import com.daesoo.war3api.mrpg.model.dto.ItemSettingResponseDto;
import com.daesoo.war3api.mrpg.model.entity.MrpgEquipment;
import com.daesoo.war3api.mrpg.model.entity.MrpgMaterial;
import com.daesoo.war3api.mrpg.service.MrpgApiService;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/mrpg/api")
public class ItemSettingController {
    private final MrpgApiService mrpgApiService;

    @GetMapping("/equipments")
    public List<MrpgEquipment> equipments() {
        return mrpgApiService.getEquipments();
    }


    @GetMapping("/materials")
    public List<MrpgMaterial> materials() {
        return mrpgApiService.getMaterials();
    }

    @PostMapping("/itemSetting")
    public ResponseDto<ItemSettingResponseDto> createItemSetting (@RequestBody ItemSettingRequestDto itemSettingRequestDto, HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        return mrpgApiService.createItemSetting(itemSettingRequestDto, token);
    }

    @GetMapping("/itemSettings")
    public ResponseDto<List<ItemSettingResponseDto>> getItemSettings(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        return mrpgApiService.getItemSettings(token);
    }

    @GetMapping("/allItemSettings")
    public ResponseDto<List<ItemSettingResponseDto>> getShares(@ApiParam(value = "page", required = true, example = "1") @RequestParam("page") int page,
                                                                    @ApiParam(value="size", required = true, example = "10") @RequestParam("size") int size,
                                                                    @ApiParam(value="character", required = true, example = "all 또는 오피스 : 격투가") @RequestParam("character") String character) {

        return mrpgApiService.getAllItemSetting(page-1, size, character);
    }

    @GetMapping("/itemSettings/{itemSettingId}")
    public ResponseDto<ItemSettingResponseDto> getItemSetting(@PathVariable Long itemSettingId) {
        return mrpgApiService.getItemSetting(itemSettingId);
    }

    @GetMapping("/itemSettings/count")
    public ResponseDto<List<CountResponseDto>> getSettingCount() {
        return mrpgApiService.getSettingCount();
    }

    @PutMapping("/itemSetting/{id}")
    public ResponseDto<String> updateChecks(@PathVariable Long id, @RequestBody ItemSettingRequestDto itemSettingRequestDto, HttpServletRequest request) {
        return mrpgApiService.updateChecks(id, itemSettingRequestDto, request);
    }

    @DeleteMapping("/itemSetting/{id}")
    public ResponseDto<String> deleteItemSetting(@PathVariable Long id, HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        return mrpgApiService.deleteItemSetting(id, token);
    }



}
