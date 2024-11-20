package com.yfmf.footlog.domain.guest.service;

import com.yfmf.footlog.domain.guest.dto.GuestPostCreateDTO;
import com.yfmf.footlog.domain.guest.dto.GuestPostDetailDTO;
import com.yfmf.footlog.domain.guest.dto.GuestPostResponseDTO;
import com.yfmf.footlog.domain.guest.dto.GuestPostUpdateDTO;
import com.yfmf.footlog.domain.guest.entity.GuestPost;
import com.yfmf.footlog.domain.guest.enums.PostStatus;
import com.yfmf.footlog.domain.guest.repository.GuestPostRepository;
import com.yfmf.footlog.domain.member.domain.Member;
import com.yfmf.footlog.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GuestPostService {
    private final GuestPostRepository guestPostRepository;
    private final MemberRepository memberRepository;


    /**
     * 새로운 게스트 게시글을 생성합니다.
     *
     * @param memberId 작성자 회원 ID
     * @param dto      게시글 생성 DTO
     * @return 생성된 게시글의 응답 DTO
     */
    @Transactional
    public GuestPostResponseDTO createGuestPost(Long memberId, GuestPostCreateDTO dto) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        GuestPost guestPost = GuestPost.builder()
                .memberId(memberId)
                .availableDate(dto.getAvailableDate())
                .availableStartTime(dto.getAvailableStartTime())
                .availableEndTime(dto.getAvailableEndTime())
                .preferredPosition(dto.getPreferredPosition())
                .location(dto.getLocation())
                .description(dto.getDescription())
                .build();

        GuestPost savedPost = guestPostRepository.save(guestPost);
        return new GuestPostResponseDTO(savedPost, member);
    }


    /**
     * 상태에 따라 게스트 게시글 목록을 조회합니다.
     *
     * @param status 게시글 상태 (선택적으로 지정 가능)
     * @return 게시글 응답 DTO 리스트
     */
    public List<GuestPostResponseDTO> getAllGuestPosts(PostStatus status) {
        List<GuestPost> posts;
        if (status != null) {
            posts = guestPostRepository.findByStatus(status);
        } else {
            posts = guestPostRepository.findAll();
        }

        return posts.stream()
                .map(post -> {
                    Member member = memberRepository.findById(post.getMemberId())
                            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
                    return new GuestPostResponseDTO(post, member);
                })
                .collect(Collectors.toList());
    }

    /**
     * 특정 게시글의 상세 정보를 조회합니다.
     *
     * @param postId 게시글 ID
     * @return 상세 응답 DTO
     */
    public GuestPostDetailDTO getGuestPost(Long postId) {
        GuestPost post = guestPostRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));

        Member member = memberRepository.findById(post.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        return new GuestPostDetailDTO(post, member);
    }


    /**
     * 특정 게시글을 수정합니다.
     *
     * @param postId   게시글 ID
     * @param memberId 작성자 회원 ID
     * @param dto      게시글 수정 DTO
     * @return 수정된 게시글의 응답 DTO
     */
    @Transactional
    public GuestPostResponseDTO updateGuestPost(Long postId, Long memberId, GuestPostUpdateDTO dto) {
        GuestPost post = guestPostRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));

        if (!post.getMemberId().equals(memberId)) {
            throw new IllegalArgumentException("게시글을 수정할 권한이 없습니다.");
        }

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        post.update(
                dto.getAvailableDate(),
                dto.getAvailableStartTime(),
                dto.getAvailableEndTime(),
                dto.getPreferredPosition(),
                dto.getLocation(),
                dto.getDescription(),
                dto.getStatus()
        );

        return new GuestPostResponseDTO(post, member);
    }


    /**
     * 특정 게시글을 삭제합니다.
     *
     * @param postId   게시글 ID
     * @param memberId 작성자 회원 ID
     */
    @Transactional
    public void deleteGuestPost(Long postId, Long memberId) {
        GuestPost post = guestPostRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));

        if (!post.getMemberId().equals(memberId)) {
            throw new IllegalArgumentException("게시글을 삭제할 권한이 없습니다.");
        }

        guestPostRepository.delete(post);
    }
}