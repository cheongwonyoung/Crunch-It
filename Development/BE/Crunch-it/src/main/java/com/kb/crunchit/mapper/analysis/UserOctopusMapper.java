package com.kb.crunchit.mapper.analysis;

import com.kb.crunchit.entity.Octopus;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import software.amazon.awssdk.services.s3.endpoints.internal.Value;

@Mapper
public interface UserOctopusMapper {
    Octopus getUserOctopus(Integer userId);
}
