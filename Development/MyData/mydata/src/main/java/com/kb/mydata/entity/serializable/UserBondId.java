package com.kb.mydata.entity.serializable;

import java.io.Serializable;
import java.util.Objects;

public class UserBondId implements Serializable {
    private String bondCode;
    private int userId;

    // equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserBondId that = (UserBondId) o;
        return userId == that.userId && Objects.equals(bondCode, that.bondCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bondCode, userId);
    }
}