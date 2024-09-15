package com.kb.mydata.dto.resp;

import com.kb.mydata.entity.Test;
import lombok.Builder;
import lombok.Data;

public class TestResponseDto {
    @Data
    @Builder
    public static class Info{
        private int testId;
        private String name;
        private int price;

        public static Info fromEntity(Test test){
            return Info.builder()
                    .testId(test.getTestId())
                    .name(test.getName())
                    .price(test.getPrice())
                    .build();
        }
    }
}
