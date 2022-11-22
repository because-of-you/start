package cn.citrus.server;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * {@code @author:} wfy
 * {@code @date:} 2022/11/21
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Person {
    private String id;
}
