package team.qdu.smartclassserver.domain;


import java.io.Serializable;

public class ApiResponse<T> implements Serializable {

    public String event;

    public String msg;

    public T obj;

    public T objList;

    public ApiResponse(String event, String msg) {
        this.event = event;
        this.msg = msg;
        this.obj = null;
        this.objList = null;
    }

    public ApiResponse(String event, String msg, T obj, T objList) {
        this.event = event;
        this.msg = msg;
        this.obj = obj;
        this.objList = objList;
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
