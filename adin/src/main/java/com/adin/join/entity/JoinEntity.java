package com.adin.join.entity;

import java.util.Date;
import java.util.Objects;

public class JoinEntity {
    private String email;
    private String password;
    private String name;
    private String nickname;
    private String useYn;
    private Date createDt;
    private Date modifyDt;

    public String getEmail() {
        return email;
    }

    public JoinEntity setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public JoinEntity setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getName() {
        return name;
    }

    public JoinEntity setName(String name) {
        this.name = name;
        return this;
    }

    public String getNickname() {
        return nickname;
    }

    public JoinEntity setNickname(String nickname) {
        this.nickname = nickname;
        return this;
    }

    public String getUseYn() {
        return useYn;
    }

    public JoinEntity setUseYn(String useYn) {
        this.useYn = useYn;
        return this;
    }

    public Date getCreateDt() {
        return createDt;
    }

    public JoinEntity setCreateDt(Date createDt) {
        this.createDt = createDt;
        return this;
    }

    public Date getModifyDt() {
        return modifyDt;
    }

    public JoinEntity setModifyDt(Date modifyDt) {
        this.modifyDt = modifyDt;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JoinEntity that = (JoinEntity) o;
        return Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
}
