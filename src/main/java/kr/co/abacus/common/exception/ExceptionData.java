package kr.co.abacus.common.exception;

import lombok.Data;

@Data
public class ExceptionData {

    String code;
    String desc;
    String type;
    String status;
}
