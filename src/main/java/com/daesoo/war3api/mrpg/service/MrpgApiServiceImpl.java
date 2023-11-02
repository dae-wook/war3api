package com.daesoo.war3api.mrpg.service;

import com.daesoo.war3api.component.JwtProvider;
import com.daesoo.war3api.dto.ResponseDto;
import com.daesoo.war3api.mrpg.model.dto.*;
import com.daesoo.war3api.mrpg.model.entity.*;
import com.daesoo.war3api.mrpg.repository.*;
import com.daesoo.war3api.user.model.entity.User;
import com.daesoo.war3api.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.*;

@RequiredArgsConstructor
@Service
public class MrpgApiServiceImpl implements MrpgApiService {

    private final MrpgEquipmentRepository mrpgEquipmentRepository;
    private final MrpgMaterialRepository mrpgMaterialRepository;
    private final ItemSettingRepository itemSettingRepository;
    private final UserRepository userRepository;
    private final ItemSettingShareRepository itemSettingShareRepository;
    private final ShareCommentRepository shareCommentRepository;

    private final JwtProvider jwtProvider;


    @Override
    public List<MrpgEquipment> getEquipments() {
        return mrpgEquipmentRepository.findAll();
    }

    @Override
    public List<MrpgMaterial> getMaterials() {
        return mrpgMaterialRepository.findAll();
    }

    @Override
    public TreeSet<String> getDropMonsters() {

        List<MrpgMaterial> materials = mrpgMaterialRepository.findAll();
        List<MrpgEquipment> equipments = mrpgEquipmentRepository.findAll();
        List<MrpgItem> mrpgItems = new ArrayList<>();
        mrpgItems.addAll(materials);
        mrpgItems.addAll(equipments);


        TreeSet<String> set = new TreeSet<>();
        for (MrpgItem s : mrpgItems) {
            if (s.getDropMonster() != null) {
                set.add(s.getDropMonster());
            }
        }

        return set;
    }

    @Transactional
    @Override
    public ResponseDto<String> createEquipment(MrpgEquipmentDto mrpgEquipmentDto) {
        MrpgEquipment mrpgEquipment = new MrpgEquipment(mrpgEquipmentDto);
        mrpgEquipmentRepository.save(mrpgEquipment);
        return ResponseDto.success("저장 성공");
    }

    @Override
    public ResponseDto<ItemSettingResponseDto> createItemSetting(ItemSettingRequestDto itemSettingRequestDto, String token) {
        if (jwtProvider.checkJwt(token)) {
            String siteNick = jwtProvider.siteNick(token);
            User user = userRepository.findBySiteNick(siteNick).orElseThrow(() -> new NullPointerException("존재하지 않는 유저입니다."));

            ItemSetting itemSetting = new ItemSetting(itemSettingRequestDto, user);

            itemSettingRepository.save(itemSetting);

            return ResponseDto.success(new ItemSettingResponseDto(itemSetting));
        } else {
            throw new IllegalArgumentException("유효하지 않은 토큰입니다.");
        }
    }

    @Transactional
    @Override
    public ResponseDto<String> updateChecks(Long id, ItemSettingRequestDto itemSettingRequestDto, HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (jwtProvider.checkJwt(token)) {

            User user = userRepository.findBySiteNick(jwtProvider.siteNick(token)).orElseThrow(() -> new NullPointerException("존재하지 않는 유저입니다."));
            ItemSetting itemSetting = itemSettingRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("등록되지 않은 아이템세팅"));
            if (user.getSiteNick().equals(itemSetting.getUser().getSiteNick())) {
                itemSetting.update(itemSettingRequestDto);
                return ResponseDto.success("등록 성공");
            } else {
                throw new IllegalArgumentException("작성자만 삭제 할 수 있습니다.");
            }
        } else {
            throw new IllegalArgumentException("유효하지 않은 토큰입니다.");
        }

    }

    @Override
    public ResponseDto<List<ItemSettingResponseDto>> getItemSettings(String token) {
        if (jwtProvider.checkJwt(token)) {
            User user = userRepository.findBySiteNick(jwtProvider.siteNick(token)).orElseThrow(() -> new NullPointerException("존재하지 않는 유저입니다."));
            List<ItemSetting> itemSettingList = itemSettingRepository.findAllByUserOrderByRegDtDesc(user);
            List<ItemSettingResponseDto> list = new ArrayList<>();
            for (ItemSetting is : itemSettingList) {
                list.add(new ItemSettingResponseDto(is));
            }
            return ResponseDto.success(list);
        } else {
            throw new IllegalArgumentException("유효하지 않은 토큰입니다.");
        }
    }

    @Override
    public ResponseDto<String> deleteItemSetting(Long id, String token) {
        if (jwtProvider.checkJwt(token)) {
            User user = userRepository.findBySiteNick(jwtProvider.siteNick(token)).orElseThrow(() -> new NullPointerException("존재하지 않는 유저입니다."));
            ItemSetting itemSetting = itemSettingRepository.findById(id).orElseThrow(() -> new NullPointerException("등록되지 않은 아이템세팅"));
            if (user.getSiteNick().equals(itemSetting.getUser().getSiteNick())) {
                itemSettingRepository.deleteById(id);
            } else {
                throw new IllegalArgumentException("작성자만 삭제 할 수 있습니다.");
            }
        } else {
            throw new IllegalArgumentException("유효하지 않은 토큰입니다.");
        }
        return null;
    }

    @Override
    public ResponseDto<String> createMaterial(MrpgMaterialRequestDto mrpgMaterialRequestDto) {
        MrpgMaterial material = new MrpgMaterial(mrpgMaterialRequestDto);
        mrpgMaterialRepository.save(material);
        return ResponseDto.success("저장 성공");
    }

    @Override
    public ResponseDto<List<ItemSettingShareResponseDto>> getShares(int page, int size, String sortBy, String character) {

        Sort sort = Sort.by(Sort.Direction.DESC, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<ItemSettingShare> shares;
        List<ItemSettingShareResponseDto> dtoList = new ArrayList<>();
        if (character.equals("all")) {
            shares = itemSettingShareRepository.findAll(pageable);
        } else {
            shares = itemSettingShareRepository.findAllByCharacterName(character, pageable);
        }
        Iterator<ItemSettingShare> iterator = shares.iterator();
        while (iterator.hasNext()) {
            dtoList.add(ItemSettingShareResponseDto.of(iterator.next()));
        }
        return ResponseDto.success(dtoList);
    }

    @Override
    public ResponseDto<ItemSettingShareResponseDto> createShare(Long settingId, ItemSettingShareRequestDto requestDto, HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (jwtProvider.checkJwt(token)) {
            User user = userRepository.findBySiteNick(jwtProvider.siteNick(token)).orElseThrow(() -> new NullPointerException("존재하지 않는 유저입니다."));
            ItemSetting itemSetting = itemSettingRepository.findById(settingId).orElseThrow(() -> new NullPointerException("잘못된 아이템세팅 ID"));
            ItemSettingShare itemSettingShare = new ItemSettingShare(requestDto, user, itemSetting);
            itemSettingShareRepository.save(itemSettingShare);
            return ResponseDto.success(ItemSettingShareResponseDto.of(itemSettingShare));
        } else {
            throw new IllegalArgumentException("유효하지 않은 토큰입니다.");
        }
    }

    @Override
    @Transactional
    public ResponseDto<ShareCommentResponseDto> createShareComment(Long shareId, CommentRequestDto requestDto, HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (jwtProvider.checkJwt(token)) {
            User user = userRepository.findBySiteNick(jwtProvider.siteNick(token)).orElseThrow(() -> new NullPointerException("존재하지 않는 유저입니다."));
            ItemSettingShare itemSettingShare = itemSettingShareRepository.findById(shareId).orElseThrow(() -> new IllegalArgumentException("잘못된 빌드공유 ID"));
            ShareComment shareComment = new ShareComment(requestDto.getContent(), user, itemSettingShare);
            shareCommentRepository.save(shareComment);
            itemSettingShare.addCommentCount();
            return ResponseDto.success(ShareCommentResponseDto.of(shareComment));
        } else {
            throw new IllegalArgumentException("유효하지 않은 토큰입니다.");
        }
    }

    @Override
    public ResponseDto<List<ShareCommentResponseDto>> getShareComments(Long shareId) {
        ItemSettingShare itemSettingShare = itemSettingShareRepository.findById(shareId).orElseThrow(() -> new IllegalArgumentException("잘못된 빌드공유 ID"));
        List<ShareComment> comments = shareCommentRepository.findAllByItemSettingShareOrderByRegDtDesc(itemSettingShare);
        List<ShareCommentResponseDto> dtoList = new ArrayList<>();
        for (ShareComment comment : comments) {
            dtoList.add(ShareCommentResponseDto.of(comment));
        }
        return ResponseDto.success(dtoList);
    }

    @Override
    @Transactional
    public ResponseDto<String> copySetting(Long shareId, HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (jwtProvider.checkJwt(token)) {
            //setting을 복사
            User user = userRepository.findBySiteNick(jwtProvider.siteNick(token)).orElseThrow(() -> new NullPointerException("존재하지 않는 유저입니다."));
            ItemSettingShare itemSettingShare = itemSettingShareRepository.findById(shareId).orElseThrow(() -> new NullPointerException("해당 아이템 세팅공유가 존재하지 않습니다. ID : " + shareId));
            ItemSetting newSetting = ItemSetting.copyItemSetting(itemSettingShare, user);
            itemSettingShare.setCopyCount(itemSettingShare.getCopyCount() + 1);
            itemSettingRepository.save(newSetting);
            return ResponseDto.success("복사 성공");
        } else {
            throw new IllegalArgumentException("유효하지 않은 토큰입니다.");
        }
    }

    @Override
    @Transactional
    public ResponseDto<String> deleteComment(Long commentId, HttpServletRequest request) {

        String token = request.getHeader("Authorization");
        if (jwtProvider.checkJwt(token)) {
            User user = userRepository.findBySiteNick(jwtProvider.siteNick(token)).orElseThrow(() -> new NullPointerException("존재하지 않는 유저입니다."));
            ShareComment comment = shareCommentRepository.findById(commentId).orElseThrow(() -> new NullPointerException("해당 댓글이 존재하지 않습니다. ID : " + commentId));
            ItemSettingShare share = itemSettingShareRepository.findById(comment.getItemSettingShare().getId()).orElseThrow(() -> new NullPointerException("해당 아이템 세팅공유가 존재하지 않습니다. ID : " + comment.getItemSettingShare().getId()));
            //작성자와 로그인 유저가 일치 or 어드민
            if (user.getSiteNick().equals(comment.getUser().getSiteNick()) || user.getRole().equals("ADMIN")) {
                //댓글 삭제
                shareCommentRepository.deleteById(commentId);
                share.subtractCommentCount();
                return ResponseDto.success("댓글 삭제 성공");
            } else {
                throw new IllegalArgumentException("작성자만 삭제할 수 있습니다.");
            }
        } else {
            throw new IllegalArgumentException("유효하지 않은 토큰입니다.");
        }
    }

    @Override
    public ResponseDto<String> deleteShare(Long shareId, HttpServletRequest request) {

        String token = request.getHeader("Authorization");
        if (jwtProvider.checkJwt(token)) {
            User user = userRepository.findBySiteNick(jwtProvider.siteNick(token)).orElseThrow(() -> new NullPointerException("존재하지 않는 유저입니다."));
            ItemSettingShare itemSettingShare = itemSettingShareRepository.findById(shareId).orElseThrow(() -> new NullPointerException("해당 빌드공유가 존재하지 않습니다. ID : " + shareId));
            if (user.getSiteNick().equals(itemSettingShare.getUser().getSiteNick()) || user.getRole().equals("ADMIN")) {
                itemSettingShareRepository.deleteById(shareId);
                return ResponseDto.success("빌드공유 삭제 성공");
            } else {
                throw new IllegalArgumentException("작성자만 삭제할 수 있습니다.");
            }
        } else {
            throw new IllegalArgumentException("유효하지 않은 토큰입니다.");
        }
    }

    @Transactional
    @Override
    public ResponseDto<String> updateShare(Long shareId, ItemSettingShareRequestDto requestDto, HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (jwtProvider.checkJwt(token)) {
            User user = userRepository.findBySiteNick(jwtProvider.siteNick(token)).orElseThrow(() -> new NullPointerException("존재하지 않는 유저입니다."));
            ItemSettingShare itemSettingShare = itemSettingShareRepository.findById(shareId).orElseThrow(() -> new NullPointerException("해당 빌드공유가 존재하지 않습니다. ID : " + shareId));

            if (user.getSiteNick().equals(itemSettingShare.getUser().getSiteNick()) || user.getRole().equals("ADMIN")) {
                itemSettingShare.update(requestDto);
                return ResponseDto.success("빌드공유 수정 성공");
            } else {
                throw new IllegalArgumentException("작성자만 수정할 수 있습니다.");
            }
        } else {
            throw new IllegalArgumentException("유효하지 않은 토큰입니다.");
        }
    }

    @Transactional
    @Override
    public ResponseDto<ShareCommentResponseDto> updateComment(Long commentId, CommentRequestDto requestDto, HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (jwtProvider.checkJwt(token)) {
            User user = userRepository.findBySiteNick(jwtProvider.siteNick(token)).orElseThrow(() -> new NullPointerException("존재하지 않는 유저입니다."));
            ShareComment shareComment = shareCommentRepository.findById(commentId).orElseThrow(() -> new NullPointerException("해당 댓글이 존재하지 않습니다. ID : " + commentId));
            if (user.getSiteNick().equals(shareComment.getUser().getSiteNick()) || user.getRole().equals("ADMIN")) {
                shareComment.update(requestDto);
                return ResponseDto.success(ShareCommentResponseDto.of(shareComment));
            } else {
                throw new IllegalArgumentException("작성자만 수정할 수 있습니다.");
            }
        } else {
            throw new IllegalArgumentException("유효하지 않은 토큰입니다.");
        }
    }

    @Override
    public ResponseDto<List<ItemSettingShareItemsResponseDto>> getShareItems() {
        List<ItemSettingShare> shareList = itemSettingShareRepository.findAll();
        List<ItemSettingShareItemsResponseDto> dtoList = new ArrayList<>();
        for (ItemSettingShare itemSettingShare : shareList) {
            dtoList.add(new ItemSettingShareItemsResponseDto(itemSettingShare));
        }
//        ItemSettingShareItemsResponseDto responseDto = new ItemSettingShareItemsResponseDto();
//        responseDto.setItems(dtoList);
        return ResponseDto.success(dtoList);
    }

    @Override
    public ResponseDto<ItemSettingShareResponseDto> getShare(Long shareId) {
        ItemSettingShare itemSettingShare = itemSettingShareRepository.findById(shareId).orElseThrow(() -> new NullPointerException("해당 빌드공유가 존재하지 않습니다."));

        return ResponseDto.success(ItemSettingShareResponseDto.of(itemSettingShare));
    }

    @Override
    public ResponseDto<List<CountResponseDto>> getCount() {
        List<CountResponseDto> dtoList = new ArrayList<>();
        String[] characters = {"루리아 : 수호자", "루미나스 : 궁수", "르무 : 거너", "리스타 : 배틀메이지", "메이미트 : 기계공", "모리스 : 점성술사", "미아 : 소환사", "베아트리스 : 성기사", "비스체 : 어쌔신", "사쿠야 : 싸움꾼", "샤르티아 : 무도가", "샬롯 : 마법사(번개)", "세리스 : 마법사(얼음)", "세실리아 : 드루이드", "시리스 : 광전사", "실프 : 검성", "아이리스 : 저격수", "알리시아 : 성직자", "엘레나 : 인형사", "엘시아 : 원소술사", "오피스 : 격투가", "유클레이스 : 사령술사", "이리스 : 살육자", "이피아 : 죽음의 기사", "일루시아 : 마법사(화염)", "카미시아 : 검혼","레스티나 : 마법사(어둠)"};
        for (String character : characters) {
            Long count = itemSettingShareRepository.countByCharacterName(character);
            dtoList.add(new CountResponseDto(character, count));
        }
        return ResponseDto.success(dtoList);
    }

    @Override
    public ResponseDto<List<ItemSettingResponseDto>> getAllItemSetting(int page, int size, String character) {
        Sort sort = Sort.by(Sort.Direction.DESC, "regDt");
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<ItemSetting> settings;
        List<ItemSettingResponseDto> dtoList = new ArrayList<>();
        if (character.equals("all")) {
            settings = itemSettingRepository.findAllByChecksIsNotNullOrChecksIsNotContainingIgnoreCase("[]", pageable);
        } else {
            settings = itemSettingRepository.findAllByCharacterNameAndChecksIsNotNullAndChecksIsNotContainingIgnoreCase(character, "[]", pageable);
        }
        for (ItemSetting setting : settings) {
            ItemSettingResponseDto responseDto = ItemSettingResponseDto.of(setting);
//            if(responseDto.getChecks() != null)
            dtoList.add(responseDto);
        }
        return ResponseDto.success(dtoList);
    }

    @Override
    public ResponseDto<ItemSettingResponseDto> getItemSetting(Long itemSettingId) {
        ItemSetting itemSetting = itemSettingRepository.findById(itemSettingId).orElseThrow(() -> new NullPointerException("잘못된 아이템세팅 ID"));
        return ResponseDto.success(new ItemSettingResponseDto(itemSetting));
    }

    @Override
    public ResponseDto<List<CountResponseDto>> getSettingCount() {
        List<CountResponseDto> dtoList = new ArrayList<>();
        String[] characters = {"루리아 : 수호자", "루미나스 : 궁수", "르무 : 거너", "리스타 : 배틀메이지", "메이미트 : 기계공", "모리스 : 점성술사", "미아 : 소환사", "베아트리스 : 성기사", "비스체 : 어쌔신", "사쿠야 : 싸움꾼", "샤르티아 : 무도가", "샬롯 : 마법사(번개)", "세리스 : 마법사(얼음)", "세실리아 : 드루이드", "시리스 : 광전사", "실프 : 검성", "아이리스 : 저격수", "알리시아 : 성직자", "엘레나 : 인형사", "엘시아 : 원소술사", "오피스 : 격투가", "유클레이스 : 사령술사", "이리스 : 살육자", "이피아 : 죽음의 기사", "일루시아 : 마법사(화염)", "카미시아 : 검혼","레스티나 : 마법사(어둠)"};
        for (String character : characters) {
            Long count = itemSettingRepository.countByCharacterNameAndChecksIsNotNullAndChecksIsNotContainingIgnoreCase(character, "[]");
            dtoList.add(new CountResponseDto(character, count));
        }
        return ResponseDto.success(dtoList);
    }
}
