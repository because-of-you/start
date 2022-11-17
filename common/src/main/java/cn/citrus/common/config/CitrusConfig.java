package cn.citrus.common.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * {@code @author:} wfy
 * {@code @date:} 2022/11/17
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CitrusConfig {
    private static String version = "0.0.1";

    public void test() {
        record Person(String name, String address) {
        }
        Person person = new Person("1", "3");
        String address = person.address();
    }
}
