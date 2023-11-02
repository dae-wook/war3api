package com.daesoo.war3api.mrpg.controller;

import com.daesoo.war3api.dto.ResponseDto;
import com.daesoo.war3api.mrpg.model.dto.*;
import com.daesoo.war3api.mrpg.service.MrpgApiService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.List;
@RequiredArgsConstructor
@RestController
@RequestMapping("/mrpg/api")
public class ItemShareController {
    private final MrpgApiService mrpgApiService;

    @ApiOperation(value="빌드공유목록 조회", notes = "page,size,character를 통해 빌드공유 목록 조회")
    @GetMapping("/itemShares")
    public ResponseDto<List<ItemSettingShareResponseDto>> getShares(@ApiParam(value = "page", required = true, example = "1") @RequestParam("page") int page,
                                                                    @ApiParam(value="size", required = true, example = "10") @RequestParam("size") int size,
                                                                    @ApiParam(value="column", required = true, example = "copyCount") @RequestParam("sortBy") String sortBy,
                                                                    @ApiParam(value="character", required = true, example = "all 또는 오피스 : 격투가") @RequestParam("character") String character) {


        return mrpgApiService.getShares(page-1, size, sortBy, character);
    }

    @GetMapping("/itemShare/{shareId}")
    public ResponseDto<ItemSettingShareResponseDto> getShare(@PathVariable Long shareId) {
        return mrpgApiService.getShare(shareId);
    }

    @GetMapping("/itemShare/items")
    public ResponseDto<List<ItemSettingShareItemsResponseDto>> getShareItems() {
        return mrpgApiService.getShareItems();
    }

    @GetMapping("/itemShare/count")
    public ResponseDto<List<CountResponseDto>> getCount() {
        return mrpgApiService.getCount();
    }


    @PostMapping("/itemShare/{settingId}")
    public ResponseDto<ItemSettingShareResponseDto> createShare(@PathVariable Long settingId, @RequestBody ItemSettingShareRequestDto requestDto, HttpServletRequest request) {
        return mrpgApiService.createShare(settingId, requestDto, request);
    }

    @PostMapping("/itemShare/comment/{shareId}")
    public ResponseDto<ShareCommentResponseDto> createShareComment(@PathVariable Long shareId, @RequestBody CommentRequestDto requestDto, HttpServletRequest request) {

        return mrpgApiService.createShareComment(shareId, requestDto, request);
    }

    @GetMapping("/itemShare/comments/{shareId}")
    public ResponseDto<List<ShareCommentResponseDto>> getComments(@PathVariable Long shareId) {
        return mrpgApiService.getShareComments(shareId);
    }

    @PostMapping("/itemShare/copy/{shareId}")
    public ResponseDto<String> createCopy(@PathVariable Long shareId, HttpServletRequest request) {
        return mrpgApiService.copySetting(shareId, request);
    }

    @DeleteMapping("/itemShare/comment/{commentId}")
    public ResponseDto<String> deleteComment(@PathVariable Long commentId, HttpServletRequest request) {
        return mrpgApiService.deleteComment(commentId, request);
    }

    @DeleteMapping("/itemShare/{shareId}")
    public ResponseDto<String> deleteShare(@PathVariable Long shareId, HttpServletRequest request) {
        return mrpgApiService.deleteShare(shareId, request);
    }

    @PutMapping("/itemShare/{shareId}")
    public ResponseDto<String> updateShare(@PathVariable Long shareId, @RequestBody ItemSettingShareRequestDto requestDto, HttpServletRequest request) {
        return mrpgApiService.updateShare(shareId, requestDto, request);
    }

    @PutMapping("/itemShare/comment/{commentId}")
    public ResponseDto<ShareCommentResponseDto> updateComment(@PathVariable Long commentId, @RequestBody CommentRequestDto requestDto, HttpServletRequest request) {
        return mrpgApiService.updateComment(commentId, requestDto, request);
    }

}
