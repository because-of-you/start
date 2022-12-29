package cn.citrus.server.config;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * {@code @author:} wfy
 * {@code @date:} 2022/12/1
 **/
public class Main {
    public static void main(String[] args) {
        System.out.printf(formatView("CREATE VIEW `v_zzcx_zj_zljc_detail` AS select "));
    }

    public static String formatView(String sql) {

        Pattern pattern = Pattern.compile("CREATE ALGORITHM=UNDEFINED DEFINER=`\\w+`@`[%\\w]+` SQL SECURITY INVOKER VIEW");
        Matcher matcher = pattern.matcher(sql);
        if (matcher.find()) {
            return matcher.replaceAll("CREATE VIEW ");
        }
        return sql;
    }
}
