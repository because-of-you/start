package cn.citrus.server;

import cn.hutool.core.util.StrUtil;
import org.junit.jupiter.api.Test;

/**
 * {@code @author:} wfy
 * {@code @date:} 2022/11/22
 **/
public class UtilTest {
    @Test
    public void t1() {
        System.out.println(StrUtil.format("%s%%%%s % {{{{} {} {}", "32%%^$&*^%*4234{}", "[java.lang.RuntimeException: 执行statement异常:Access denied for user 'root'@'%' to database 'information_schema']"));
        System.out.println(String.format("%%s%%", "%"));
    }
}
