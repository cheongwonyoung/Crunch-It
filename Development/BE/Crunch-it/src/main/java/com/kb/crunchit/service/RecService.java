package com.kb.crunchit.service;

import com.kb.crunchit.entity.RecommendationApiDeposit;
import com.kb.crunchit.entity.RecommendationApiDepositOption;
import com.kb.crunchit.entity.RecommendationApiSaving;
import com.kb.crunchit.mapper.recommendation.api.RecommendationApiDepositMapper;
import com.kb.crunchit.mapper.recommendation.api.RecommendationApiDepositOptionMapper;
import com.kb.crunchit.mapper.recommendation.api.RecommendationApiSavingMapper;
import com.kb.crunchit.mapper.recommendation.api.RecommendationApiSavingOptionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecService {
    private final RecommendationApiDepositMapper recommendationApiDepositMapper;
    private final RecommendationApiDepositOptionMapper recommendationApiDepositOptionMapper;

    private final RecommendationApiSavingMapper recommendationApiSavingMapper;
    private final RecommendationApiSavingOptionMapper recommendationApiSavingOptionMapper;

    public List<RecommendationApiDeposit> getAllRecommendationDeposit(){
        return recommendationApiDepositMapper.getAllDeposits();
    }

    public void setRecommendationApiDepositList(List<RecommendationApiDeposit> depositList){
        for(RecommendationApiDeposit deposit : depositList){
            String depositId = deposit.getFinPrdtCd();
            deposit.addSixMonthOption(recommendationApiDepositOptionMapper.getSixMonthDepositOption(depositId));
            deposit.addYearOption(recommendationApiDepositOptionMapper.getYearDepositOption(depositId));
        }
    }

    public List<RecommendationApiSaving> getAllRecommendationSaving(){
        return recommendationApiSavingMapper.getAllSavings();
    }

    public void setRecommendationApiSavingList(List<RecommendationApiSaving> savingList){
        for(RecommendationApiSaving saving: savingList){
            String savingId = saving.getFinPrdtCd();
            saving.addSixMonthOption(recommendationApiSavingOptionMapper.getSixMonthDepositOption(savingId));
            saving.addYearOption(recommendationApiSavingOptionMapper.getYearDepositOption(savingId));
        }
    }
}
