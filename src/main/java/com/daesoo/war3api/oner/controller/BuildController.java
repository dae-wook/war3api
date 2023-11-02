package com.daesoo.war3api.oner.controller;

import com.daesoo.war3api.dto.ResponseDto;
import com.daesoo.war3api.mrpg.model.dto.ItemSettingShareResponseDto;
import com.daesoo.war3api.oner.model.dto.BuildRequestDto;
import com.daesoo.war3api.oner.model.dto.BuildResponseDto;
import com.daesoo.war3api.oner.service.BuildService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BuildController {

    private final BuildService buildService;

    @PostMapping("/builds")
    public ResponseDto<BuildResponseDto> createBuild(@RequestBody BuildRequestDto dto, HttpServletRequest request) {

        return ResponseDto.success(buildService.createBuild(dto, request));
    }

    @ApiOperation(value="빌드공유목록 조회", notes = "page,size,character를 통해 빌드공유 목록 조회")
    @GetMapping("/builds")
    public ResponseDto<List<BuildResponseDto>> getBuilds(@ApiParam(value = "page", required = true, example = "1") @RequestParam("page") int page,
                                                                    @ApiParam(value="size", required = true, example = "10") @RequestParam("size") int size,
                                                                    @ApiParam(value="character", required = true, example = "all 또는 루피") @RequestParam("character") String character) {


        return ResponseDto.success(buildService.getBuilds(page-1, size, character));
    }

    @ApiOperation(value="로그인 유저 빌드공유목록 조회", notes = "page,size,character를 통해 빌드공유 목록 조회")
    @GetMapping("/my/builds")
    public ResponseDto<List<BuildResponseDto>> getBuildsByLoginUser(@ApiParam(value = "page", required = true, example = "1") @RequestParam("page") int page,
                                                         @ApiParam(value="size", required = true, example = "10") @RequestParam("size") int size,
                                                         @ApiParam(value="character", required = true, example = "all 또는 루피") @RequestParam("character") String character,
                                                         HttpServletRequest request) {


        return ResponseDto.success(buildService.getBuildsByLoginUser(page-1, size, character, request));
    }

    @GetMapping("/builds/{buildId}")
    public ResponseDto<BuildResponseDto> getBuild(@PathVariable Long buildId){


        return ResponseDto.success(buildService.getBuild(buildId));
    }

    @DeleteMapping("/builds/{buildId}")
    public ResponseDto<Boolean> deleteBuild(@PathVariable Long buildId, HttpServletRequest request) {

        return ResponseDto.success(buildService.deleteBuild(buildId, request));
    }

    @PutMapping("/builds/{buildId}")
    public ResponseDto<BuildResponseDto> updateBuild(@PathVariable Long buildId, @RequestBody BuildRequestDto dto, HttpServletRequest request) {
        return ResponseDto.success(buildService.updateBuild(buildId, dto, request));
    }



}
