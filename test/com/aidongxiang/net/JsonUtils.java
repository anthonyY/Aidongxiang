package com.aidongxiang.net;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JsonUtils {


    public static String formatJson(String content) {
        StringBuffer sb = new StringBuffer();
        int index = 0;

        for(int count = 0; index < content.length(); ++index) {
            char ch = content.charAt(index);
            int i;
            if(ch != 123 && ch != 91) {
                if(ch != 125 && ch != 93) {
                    if(ch == 44) {
                        sb.append(ch);
                        sb.append('\n');

                        for(i = 0; i < count; ++i) {
                            sb.append('\t');
                        }
                    } else {
                        sb.append(ch);
                    }
                } else {
                    sb.append('\n');
                    --count;

                    for(i = 0; i < count; ++i) {
                        sb.append('\t');
                    }

                    sb.append(ch);
                }
            } else {
                sb.append(ch);
                sb.append('\n');
                ++count;

                for(i = 0; i < count; ++i) {
                    sb.append('\t');
                }
            }
        }

        return sb.toString();
    }

    public static String compactJson(String content) {
        String regEx = "[\t\n]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(content);
        return m.replaceAll("").trim();
    }
}
