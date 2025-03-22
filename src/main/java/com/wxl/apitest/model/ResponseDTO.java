package com.wxl.apitest.model;

import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class ResponseDTO {
    private JSONObject exception;

    public ResponseDTO(RequestDTO requestDTO) {
        JSONObject jsonObject = JSONObject.parseObject(requestDTO.getExceptString());
        for (String s : jsonObject.keySet()){
            exception.put(s,jsonObject.get(s).getClass());
        }
    }
}
