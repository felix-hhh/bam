package com.kelaker.kcommon.user.data;

import lombok.Data;

import java.util.Date;
import java.util.Objects;

/**
 * 用户访问数据
 */
@Data
public class UserVisitorRecordData {
    /**
     *  用户ID
     */
    private String userId;

    /**
     * 创建时间
     */
    private Date createDatetime;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserVisitorRecordData data = (UserVisitorRecordData) o;
        return userId.equals(data.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }
}
