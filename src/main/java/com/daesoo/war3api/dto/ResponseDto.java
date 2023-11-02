package com.daesoo.war3api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseDto<T> {

//    private int statusCode;
    private T result;

//    public ResponseDto (int statusCode, T data) {
//        this.statusCode = statusCode;
//        this.result = data;
//    }

    public ResponseDto (T data) {
//        this.statusCode = statusCode;
        this.result = data;
    }

    public static <T> ResponseDto<T> success(T result) {
        return new ResponseDto<>(result);
    }

    public static <T> ResponseDto<T> fail(T result) {
        return new ResponseDto<>(result);
    }

}
