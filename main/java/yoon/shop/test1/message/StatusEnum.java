package yoon.shop.test1.message;

public enum StatusEnum {

    OK("200"),
    BAD_REQUEST("400"),
    NOT_FOUND("404"),
    INTERNAL_SERVER_ERROR("500");

    String code;

    StatusEnum(String code){
        this.code = code;
    }

}
