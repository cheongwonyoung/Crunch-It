package com.kb.mydata.service;

import com.kb.mydata.dto.resp.TestResponseDto;

import java.util.List;

public interface TestService {
    public List<TestResponseDto.Info> getTestInfo();
}
