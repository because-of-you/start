package cn.citrus.server;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
        GraphFigure<Person> figure = new GraphFigure<>();
        figure.init(list);
    }
}
