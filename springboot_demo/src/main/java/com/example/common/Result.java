package com.example.common;

import com.baomidou.mybatisplus.extension.api.R;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {
    private int code;//200/400
    private String msg;//成功、失败
    private Long total;//总记录数
    private Object data;//数据
    public static Result fail(){
        return new Result(400,"失败",0L,null);
    }
    public static Result success(){
        return new Result(200,"success",0L,null);
    }
    public static Result success(Object data,Long total){
        return new Result(200,"成功",total,data);
    }

}
