package team.qdu.smartclassserver.domain;


import java.io.Serializable;

public class ApiResponse<T> implements Serializable {

    private String event;

    private String msg;

    private T obj;

    private T objList;

    public ApiResponse(){}

    public ApiResponse(String event, String msg) {
        this.event = event;
        this.msg = msg;
        this.obj = null;
        this.objList = null;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getObj() {
        return obj;
    }

    public void setObj(T obj) {
        this.obj = obj;
    }

    public T getObjList() {
        return objList;
    }

    public void setObjList(T objList) {
        this.objList = objList;
    }
}
