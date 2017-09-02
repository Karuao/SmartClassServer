package team.qdu.smartclassserver.domain;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {
    private Integer user_id;

    private String email;

    private String password;

    private String avatar;

    private String name;

    private Byte gender;

    private Date birthday;

    private Short university;

    private Short department;

    private Byte if_show_closed_classes;

    private static final long serialVersionUID = 1L;

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar == null ? null : avatar.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Byte getGender() {
        return gender;
    }

    public void setGender(Byte gender) {
        this.gender = gender;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Short getUniversity() {
        return university;
    }

    public void setUniversity(Short university) {
        this.university = university;
    }

    public Short getDepartment() {
        return department;
    }

    public void setDepartment(Short department) {
        this.department = department;
    }

    public Byte getIf_show_closed_classes() {
        return if_show_closed_classes;
    }

    public void setIf_show_closed_classes(Byte if_show_closed_classes) {
        this.if_show_closed_classes = if_show_closed_classes;
    }
}