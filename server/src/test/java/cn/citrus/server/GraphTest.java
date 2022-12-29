package cn.citrus.server;

import cn.hutool.core.lang.Editor;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import org.junit.jupiter.api.Test;

import java.util.*;

/**
 * {@code @author:} wfy
 * {@code @date:} 2022/11/21
 **/
public class GraphTest {
    @Test
    public void t1() {
        List<GraphNode<Person>> list = new ArrayList<>();
        list.add(new GraphNode<>(Person.builder().id("1").build(), Person::getId));
        list.add(new GraphNode<>(Person.builder().id("2").build(), Person::getId));
        list.add(new GraphNode<>(Person.builder().id("3").build(), Person::getId));
        list.add(new GraphNode<>(Person.builder().id("4").build(), Person::getId));
        list.add(new GraphNode<>(Person.builder().id("5").build(), Person::getId));
        list.add(new GraphNode<>(Person.builder().id("6").build(), Person::getId));
        list.add(new GraphNode<>(Person.builder().id("7").build(), Person::getId));
        GraphFigure<Person> figure = new GraphFigure<>();
        figure.init(list);
        figure.addRelationShip("1", "2");
        figure.addRelationShip("2", "3");
        figure.addRelationShip("3", "4");
        figure.addRelationShip("1", "5");
        figure.addRelationShip("4", "6");
        figure.addRelationShip("5", "6");
        figure.addRelationShip("6", "7");
        Map<Object, Object> filter = MapUtil.filter(MapUtil.newHashMap(), item -> ObjectUtil.isNotEmpty(item.getValue()));
        figure.floyd();
        String format = String.format("SELECT" +
                "  COLUMN_NAME   AS column_name" +
                ", TABLE_NAME    AS table_name" +
                ", TABLE_SCHEMA  AS table_schema" +
                ", TABLE_CATALOG AS table_catalog" +
                "  FROM information_schema.key_column_usage" +
                "  WHERE TABLE_SCHEMA = ''" +
                "    AND TABLE_NAME = ''" +
                "    AND CONSTRAINT_NAME = 'PRIMARY'" +
                "  ORDER BY ORDINAL_POSITION");
        System.out.println(format);
        System.out.println("-------");
    }
}
