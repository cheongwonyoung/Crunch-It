package com.kb.crunchit.mapper.recommendation.top;

import com.kb.crunchit.dto.response.recommendation.top.UserStockResponseDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface UserStockMapper {
    List<UserStockResponseDTO> findTop3StocksByUserId(@Param("userId") int userId);
}
