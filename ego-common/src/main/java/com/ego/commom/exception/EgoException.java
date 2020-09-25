package com.ego.commom.exception;

import com.ego.commom.enums.IException;
import lombok.Data;

/**
 * 〈〉
 *
 * @author coach tam
 * @email 327395128@qq.com
 * @create 2020/9/21
 * @since 1.0.0
 * 〈坚持灵活 灵活坚持〉
 */
@Data
public class EgoException extends RuntimeException {
    private Integer errorCode;
    private String errorMessage;

    public EgoException(IException iException) {
        this.errorCode = iException.getCode();
        this.errorMessage = iException.getMessage();
    }
}
