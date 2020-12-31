package com.yubin.hotelSys.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class NewCheckinDTO {
    private String guestId;
    private String guestName;

    @Pattern(regexp = "((男)|(女)|(保密))")
    private String guestSex;

    @Pattern(regexp = "^1(3|4|5|7|8)\\d{9}$",message = "手机号码格式错误")
    @NotBlank(message = "手机号码不能为空")
    private String guestTel;

    private String roomId;
    private String group;
    private int useDays;
    private Double discountRatio;

    @Min(0)
    private int age;
}
