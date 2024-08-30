package com.yfmf.footlog.match.dao;

import com.yfmf.footlog.match.dto.LoadMatchResponseDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MatchMapper {
    List<LoadMatchResponseDTO> findAllMatches();
}
