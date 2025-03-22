package model;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson2.JSON;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Map;

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
