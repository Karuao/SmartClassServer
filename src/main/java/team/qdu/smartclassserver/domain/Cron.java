package team.qdu.smartclassserver.domain;

import java.io.Serializable;
import java.util.Date;

public class Cron implements Serializable {
    private Integer cron_id;

    private Date time;

    private Integer homework_id;

    private static final long serialVersionUID = 1L;

    public Integer getCron_id() {
        return cron_id;
    }

    public void setCron_id(Integer cron_id) {
        this.cron_id = cron_id;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Integer getHomework_id() {
        return homework_id;
    }

    public void setHomework_id(Integer homework_id) {
        this.homework_id = homework_id;
    }
}