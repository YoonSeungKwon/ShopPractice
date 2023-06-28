package yoon.shop.test1.message;

import jdk.jshell.Snippet;
import lombok.Data;

@Data
public class Message {

    private StatusEnum statusEnum;
    private String message;
    private Object data;

    public Message(){
        this.data = null;
        this.message = null;
        this.statusEnum = StatusEnum.BAD_REQUEST;
    }

}


