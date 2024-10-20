package com.kb.crunchit.service;

import com.kb.crunchit.entity.Octopus;
import com.kb.crunchit.mapper.analysis.TransactionMapper;
import com.kb.crunchit.mapper.analysis.UserOctopusMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
public class HomeService {
    private final TransactionMapper transactionMapper;
    private final UserOctopusMapper userOctopusMapper;

    public Octopus getUserOctopus(Integer userId){
        return userOctopusMapper.getUserOctopus(userId);
    }

    public long getMonthlyOutcome(Integer userId){
        return transactionMapper.calculateTotalOutcome(userId);
    }

}
