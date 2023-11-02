package com.daesoo.war3api.oner.service;

import com.daesoo.war3api.component.JwtProvider;
import com.daesoo.war3api.dto.ResponseDto;
import com.daesoo.war3api.oner.model.dto.BuildRequestDto;
import com.daesoo.war3api.oner.model.dto.BuildResponseDto;
import com.daesoo.war3api.oner.model.entity.Build;
import com.daesoo.war3api.oner.model.repository.BuildRepository;
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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BuildService {
    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;
    private final BuildRepository buildRepository;

    public BuildResponseDto createBuild(BuildRequestDto dto, HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (jwtProvider.checkJwt(token)) {
            User user = userRepository.findBySiteNick(jwtProvider.siteNick(token)).orElseThrow(() ->new NullPointerException("유저 정보가 없습니다."));

            Build build = Build.create(dto, user);
            buildRepository.save(build);

            return BuildResponseDto.of(build);

        } else {
            throw new IllegalArgumentException("유효하지 않은 토큰입니다.");
        }
    }

    public List<BuildResponseDto> getBuilds(int page, int size, String character) {

        Sort sort = Sort.by(Sort.Direction.DESC, "regDt");
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Build> builds;
        List<BuildResponseDto> dtoList = new ArrayList<>();
        if (character.equals("all")) {
            builds = buildRepository.findAll(pageable);
        } else {
            builds = buildRepository.findAllByCharacterName(character, pageable);
        }
        Iterator<Build> iterator = builds.iterator();
        while (iterator.hasNext()) {
            dtoList.add(BuildResponseDto.of(iterator.next()));
        }
        return dtoList;
    }

    public List<BuildResponseDto> getBuildsByLoginUser(int page, int size, String character, HttpServletRequest request) {

        String token = request.getHeader("Authorization");
        if (jwtProvider.checkJwt(token)) {
            User user = userRepository.findBySiteNick(jwtProvider.siteNick(token)).orElseThrow(() ->new NullPointerException("유저 정보가 없습니다."));

            Sort sort = Sort.by(Sort.Direction.DESC, "regDt");
            Pageable pageable = PageRequest.of(page, size, sort);
            Page<Build> builds;
            List<BuildResponseDto> dtoList = new ArrayList<>();
            if (character.equals("all")) {
                builds = buildRepository.findAllByUser(user, pageable);
            } else {
                builds = buildRepository.findAllByCharacterNameAndUser(character, user, pageable);
            }

            if (user.getRole().equals("ADMIN")) {
                builds = buildRepository.findAll(PageRequest.of(page, 9999, sort));
            }

            Iterator<Build> iterator = builds.iterator();
            while (iterator.hasNext()) {
                dtoList.add(BuildResponseDto.of(iterator.next()));
            }
            return dtoList;

        } else {
            throw new IllegalArgumentException("유효하지 않은 토큰입니다.");
        }

    }

    public boolean deleteBuild(Long buildId, HttpServletRequest request) {

        String token = request.getHeader("Authorization");
        if (jwtProvider.checkJwt(token)) {
            User user = userRepository.findBySiteNick(jwtProvider.siteNick(token)).orElseThrow(() ->new NullPointerException("유저 정보가 없습니다."));
            Build build = buildRepository.findById(buildId).orElseThrow( () -> new NullPointerException("빌드 정보가 없습니다."));
            if(build.getUser().getSiteNick().equals(user.getSiteNick())) {
                buildRepository.deleteById(buildId);
                return true;
            }else {
                throw new IllegalArgumentException("작성자만 삭제 할 수 있습니다.");
            }

        } else {
            throw new IllegalArgumentException("유효하지 않은 토큰입니다.");
        }

    }

    @Transactional
    public BuildResponseDto updateBuild(Long buildId, BuildRequestDto dto, HttpServletRequest request) {

        String token = request.getHeader("Authorization");
        if (jwtProvider.checkJwt(token)) {
            User user = userRepository.findBySiteNick(jwtProvider.siteNick(token)).orElseThrow(() ->new NullPointerException("유저 정보가 없습니다."));
            Build build = buildRepository.findById(buildId).orElseThrow( () -> new NullPointerException("빌드 정보가 없습니다."));
            if(build.getUser().getSiteNick().equals(user.getSiteNick())) {
                build.update(dto);
                return BuildResponseDto.of(build);
            }else {
                throw new IllegalArgumentException("작성자만 수정 할 수 있습니다.");
            }

        } else {
            throw new IllegalArgumentException("유효하지 않은 토큰입니다.");
        }
    }

    public BuildResponseDto getBuild(Long buildId) {
        return BuildResponseDto.of(buildRepository.findById(buildId).orElseThrow( () -> new NullPointerException("빌드 정보가 없습니다.")));
    }
}
