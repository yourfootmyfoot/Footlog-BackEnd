package com.yfmf.footlog.match.model.dao;

import com.yfmf.footlog.match.model.dto.LoadMatchResponseDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MatchMapper {
    List<LoadMatchResponseDTO> findAllMatches();
}
