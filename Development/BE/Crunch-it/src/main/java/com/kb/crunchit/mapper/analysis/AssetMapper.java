package com.kb.crunchit.mapper.analysis;

import com.kb.crunchit.dto.response.analysis.AssetResponseDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AssetMapper {
    AssetResponseDto getMonthStatistics(@Param("userId") Integer userId,
                                        @Param("year") Integer year,
                                        @Param("month") Integer month);
}
