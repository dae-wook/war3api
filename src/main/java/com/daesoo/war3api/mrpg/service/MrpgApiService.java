package com.daesoo.war3api.mrpg.service;

import com.daesoo.war3api.dto.ResponseDto;
import com.daesoo.war3api.mrpg.model.dto.*;
import com.daesoo.war3api.mrpg.model.entity.ItemSettingShare;
import com.daesoo.war3api.mrpg.model.entity.MrpgEquipment;
import com.daesoo.war3api.mrpg.model.entity.MrpgMaterial;
import org.springframework.data.domain.Page;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.TreeSet;

public interface MrpgApiService {
    List<MrpgEquipment> getEquipments();

    List<MrpgMaterial> getMaterials();

    TreeSet<String> getDropMonsters();

    ResponseDto<String> createEquipment(MrpgEquipmentDto mrpgEquipmentDto);

    ResponseDto<ItemSettingResponseDto> createItemSetting(ItemSettingRequestDto itemSettingRequestDto,String token);

    ResponseDto<String> updateChecks(Long id, ItemSettingRequestDto itemSettingRequestDto, HttpServletRequest request);

    ResponseDto<List<ItemSettingResponseDto>> getItemSettings(String token);

    ResponseDto<String> deleteItemSetting(Long id, String token);

    ResponseDto<String> createMaterial(MrpgMaterialRequestDto mrpgMaterialRequestDto);

    ResponseDto<List<ItemSettingShareResponseDto>> getShares(int page, int size, String sortBy, String character);

    ResponseDto<ItemSettingShareResponseDto> createShare(Long settingId, ItemSettingShareRequestDto requestDto, HttpServletRequest request);

    ResponseDto<ShareCommentResponseDto> createShareComment(Long shareId, CommentRequestDto requestDto, HttpServletRequest request);

    ResponseDto<List<ShareCommentResponseDto>> getShareComments(Long shareId);

    ResponseDto<String> copySetting(Long shareId, HttpServletRequest request);

    ResponseDto<String> deleteComment(Long commentId, HttpServletRequest request);

    ResponseDto<String> deleteShare(Long shareId, HttpServletRequest request);

    ResponseDto<String> updateShare(Long shareId, ItemSettingShareRequestDto requestDto, HttpServletRequest request);

    ResponseDto<ShareCommentResponseDto> updateComment(Long commentId, CommentRequestDto requestDto, HttpServletRequest request);

    ResponseDto<List<ItemSettingShareItemsResponseDto>> getShareItems();

    ResponseDto<ItemSettingShareResponseDto> getShare(Long shareId);

    ResponseDto<List<CountResponseDto>> getCount();

    ResponseDto<List<ItemSettingResponseDto>> getAllItemSetting(int i, int size, String character);

    ResponseDto<ItemSettingResponseDto> getItemSetting(Long itemSettingId);

    ResponseDto<List<CountResponseDto>> getSettingCount();
}
