package com.itzyf.filterdemo;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author 依风听雨
 * @version 创建时间：2017/5/23 15:26
 */

public class LabelBean {
    public static final int HEADER = 0;
    public static final int LABEL = 1;

    @IntDef({HEADER, LABEL})
    @Retention(RetentionPolicy.SOURCE)
    public @interface TYPE {
    }

    private String label;
    private int type;
    private String groupName;
    private boolean isChecked = false;

    public LabelBean(String label, String groupName) {
        this.label = label;
        this.type = LABEL;
        this.groupName = groupName;
    }

    public LabelBean(String label, int type) {
        this.label = label;
        this.type = type;
        this.groupName = label;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @TYPE
    public int getType() {
        return type;
    }

    public void setType(@TYPE int type) {
        this.type = type;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
