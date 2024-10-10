package com.kb.crunchit.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailRequestDto {
    public String email;
    public boolean isForSignUp;
}
