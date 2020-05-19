package com.zm.fileupload.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @Author zhangming
 * @Date 2020/5/18 22:44
 */
@Getter
@Setter
@AllArgsConstructor
public class GlobalResult {
    private Boolean status;
    private String message;
}
