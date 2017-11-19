package com.papaolabs.api.domain.service.util;

public class KorStringUtils {
    StringBuffer snt;
    static String[] josalist = new String[]{
        "은", "는",
        "이", "가",
        "을", "를",
        "과", "와",
        "으로", "로",
        "으로서", "로서",
        "으로서의", "로서의",
        "으로써", "로써",
        };

    public KorStringUtils() {
        this(null);
    }

    public KorStringUtils(String snt) {
        this.snt = new StringBuffer(snt == null ? "" : snt);
    }

    public boolean hasFinalConsonant() {
        if (snt == null || snt.length() == 0) {
            return false;
        }
        int code = snt.substring(snt.length() - 1)
                      .codePointAt(0);
        //한글 문자이고 받침이 존재함
        if (code >= 44032 && code <= 55203 && (code - 44032) % 28 > 0) {
            return true;
        }
        return false;
    }

    public KorStringUtils appendOnly(String str) {
        this.snt.append(str);
        return this;
    }

    public KorStringUtils append(String str) {
        int idx = str.indexOf(" ");
        if (idx <= 0) {
            return appendOnly(str);
        } else {
            return appendJosa(str.substring(0, idx)).appendOnly(str.substring(idx));
        }
    }

    public KorStringUtils clear() {
        this.snt = new StringBuffer();
        return this;
    }

    public KorStringUtils appendJosa(String josa) {
        if (this.hasFinalConsonant()) {
            for (int i = 0; i < josalist.length; ++i) {
                if (josa.equals(josalist[i])) {
                    if (i % 2 == 0) {
                        return appendOnly(josalist[i]);
                    } else {
                        return appendOnly(josalist[i - 1]);
                    }
                }
            }
        } else {
            for (int i = 0; i < josalist.length; ++i) {
                if (josa.equals(josalist[i])) {
                    if (i % 2 == 0) {
                        return appendOnly(josalist[i + 1]);
                    }
                    return appendOnly(josalist[i]);
                }
            }
        }
        return appendOnly(josa);
    }

    public String toString() {
        return this.snt.toString();
    }
}
