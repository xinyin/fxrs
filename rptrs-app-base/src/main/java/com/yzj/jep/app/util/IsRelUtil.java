package com.yzj.jep.app.util;

import com.yzj.jep.app.bus.model.RelEnum;

public class IsRelUtil {
    public static String contains(String relName) {

        for (RelEnum r : RelEnum.values()) {
            if (r.getIsName().equals(relName)) {
                return r.getName();
            }
        }

        return null;
    }
}
