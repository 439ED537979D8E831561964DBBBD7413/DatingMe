package com.acemurder.datingme.modules.dating.event;

/**
 * Created by zhengyuxuan on 16/8/27.
 */

public class DatingItemTypeChangeEvent {
    public boolean isOnlyNotDating() {
        return isOnlyNotDating;
    }

    private boolean isOnlyNotDating = false;

    public DatingItemTypeChangeEvent(boolean isOnlyNotDating) {
        this.isOnlyNotDating = isOnlyNotDating;
    }
}
