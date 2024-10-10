package com.kb.crunchit.controller;

import com.kb.crunchit.dto.request.UserRequestDto;
import com.kb.crunchit.dto.response.UserDto;
import com.kb.crunchit.entity.User;
import com.kb.crunchit.security.CustomUserDetails;
import com.kb.crunchit.service.MypageService;
import com.kb.crunchit.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mypage")
public class MypageController {
    private final MypageService mypageService;
    private final S3Service s3Service;

    private
    @GetMapping("/userInfo")
    ResponseEntity<Map<String, Object>> getUserInfo(Authentication auth){
        Map<String, Object> result = new HashMap<>();
        CustomUserDetails customUserDetails = (CustomUserDetails) auth.getPrincipal();
        String email = customUserDetails.getUsername();
        UserDto userDto = mypageService.getUserInfo(email);
        result.put("code" , 200);
        result.put("data", userDto);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/updateUserInfo")
    ResponseEntity<Map<String,Object>> updateUserInfo(@RequestBody UserRequestDto dto, Authentication auth){
        Map<String,Object> result = new HashMap<>();
        CustomUserDetails customUserDetails = (CustomUserDetails) auth.getPrincipal();
        String email = customUserDetails.getUsername();
        dto.setEmail(email);
        mypageService.updateUser(dto);
        result.put("code" , 200);
        result.put("message", "수정되었습니다.");
        return ResponseEntity.ok(result);
    }

    @PostMapping("/uploadProfile")
    ResponseEntity<Map<String,Object>> uploadProfile(@RequestParam("file")MultipartFile file, Authentication auth){
        String fileName = file.getOriginalFilename();
        CustomUserDetails customUserDetails = (CustomUserDetails) auth.getPrincipal();
        String email = customUserDetails.getUsername();
        Map<String,Object> resultMap = new HashMap<>();
        // 파일 이름이 없으면 false 반환
        if (fileName == null) {
            resultMap.put("message", "파일 이름이 없습니다.");
            return new ResponseEntity<>(resultMap,HttpStatus.BAD_REQUEST);
        }

        String extension = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        String[] imageExtensions = {"jpg", "jpeg", "png", "gif", "bmp"};
        Set<String> imageExtensionSet = new HashSet<>(Arrays.asList(imageExtensions));

        // 확장자가 이미지 파일 목록에 있는지 확인
        if(!imageExtensionSet.contains(extension)){
            resultMap.put("message", "지원하지 않는 확장자입니다.");
            return new ResponseEntity<>(resultMap,HttpStatus.BAD_REQUEST);
        }

        try{
            File tempFile = File.createTempFile(fileName, null);
            file.transferTo(tempFile);
            String uuidFileName = UUID.randomUUID() +"."+extension;
            String userProfile = mypageService.getUserProfile(email);
            if(userProfile != null){
                String keyName = s3Service.extractKeyName(userProfile);
                if(keyName!=null) s3Service.deleteFile(keyName);
            }
            String newProfileUrl = s3Service.uploadFile(uuidFileName, tempFile);

            User user = User.builder()
                    .email(email)
                    .profileUrl(newProfileUrl)
                    .build();

            mypageService.updateUserProfile(user);
            resultMap.put("message", "프로필 변경 완료되었습니다.");
            resultMap.put("profileURL", newProfileUrl);
        }
        catch(Exception e){
            return new ResponseEntity<>(resultMap,HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(resultMap);

    }
}
