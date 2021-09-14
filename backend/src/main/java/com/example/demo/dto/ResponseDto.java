package com.example.demo.dto;

import lombok.Getter;

@Getter
public class ResponseDto {
    private String status;
    private String msg;

    public ResponseDto(String status, String msg) {
        this.status = status;
        this.msg = msg;
    }
}
