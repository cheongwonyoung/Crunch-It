package com.kb.mydata.service;



import com.kb.mydata.dto.resp.AccountResponseDto;

import java.util.List;

public interface RetrieveService {
    public List<AccountResponseDto.Info> getAccountInfo(Long userId);
}
