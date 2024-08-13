package com.yfmf.footlog.match.command.domain.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MatchService {

    private LoadMatchInfoResponseDTO loadMatchInfoResponseDTO;

    @Autowired
    public MatchService(LoadMatchInfoResponseDTO loadMatchInfoResponseDTO) {
        this.loadMatchInfoResponseDTO = loadMatchInfoResponseDTO;
    }


}
