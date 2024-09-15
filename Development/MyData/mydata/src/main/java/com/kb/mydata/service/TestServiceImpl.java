package com.kb.mydata.service;

import com.kb.mydata.dto.resp.TestResponseDto;
import com.kb.mydata.entity.Test;
import com.kb.mydata.repository.TestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("TestService")
@RequiredArgsConstructor
public class TestServiceImpl implements TestService{
    private final TestRepository testRepository;


    @Override
    public List<TestResponseDto.Info> getTestInfo(){
        List<TestResponseDto.Info> res = new ArrayList<>();

        List<Test> testList = testRepository.findAll();

        for(Test test : testList){
            TestResponseDto.Info info = TestResponseDto.Info.fromEntity(test);
            res.add(info);
        }

        return res;
    }

}
