package com.yfmf.footlog.domain.ask.service;

import com.yfmf.footlog.domain.ask.dto.AskCreateRequestDto;
import com.yfmf.footlog.domain.ask.dto.AskCreateResponseDto;
import com.yfmf.footlog.domain.ask.dto.AskResponseDto;
import com.yfmf.footlog.domain.ask.entity.Ask;
import com.yfmf.footlog.domain.ask.repository.AskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AskService {

    private final AskRepository askRepository;

    public AskCreateResponseDto createAsk(AskCreateRequestDto requestDto) {

        Ask savedAsk = askRepository.save(requestDto.toEntity());

        AskCreateResponseDto responseDto = new AskCreateResponseDto();

        responseDto.setAskId(savedAsk.getId());
        responseDto.setUserId(savedAsk.getUserId());
        responseDto.setCategory(savedAsk.getCategory());
        responseDto.setTitle(savedAsk.getTitle());
        responseDto.setContent(savedAsk.getContent());
        responseDto.setAnswered(savedAsk.getAnswered());

        return responseDto;
    }

    public List<AskResponseDto> getAllAsks() {

        List<Ask> asks = askRepository.findAll();

        List<AskResponseDto> responseDtos = new ArrayList<>();

        for (Ask ask : asks) {
            AskResponseDto responseDto = ask.toResponseDto();
            responseDto.setAskId(ask.getId());

            responseDtos.add(responseDto);
        }

        return responseDtos;
    }
}
