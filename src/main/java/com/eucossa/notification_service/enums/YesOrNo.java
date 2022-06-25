package com.eucossa.notification_service.enums;

/**
 * @author christopherochiengotieno@gmail.com
 * @version 1.0.0
 * @since Saturday, 25/06/2022
 */
public enum YesOrNo {
    Y("Yes"), N("No");

    private final String description;

    YesOrNo(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
