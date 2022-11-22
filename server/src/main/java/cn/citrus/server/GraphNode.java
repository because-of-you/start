package cn.citrus.server;

import lombok.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/**
 * {@code @author:} wfy
 * {@code @date:} 2022/11/21
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(exclude = {"key", "target", "source"})
public class GraphNode<T> {
    private T data;
    private Function<T, ?> key;
    /**
     * 入边
     */
    private List<GraphNode<T>> source;
    /**
     * 出边
     */
    private List<GraphNode<T>> target;
    /**
     * 保存节点关系
     */
    private Map<String, GraphNode<T>> relationships;
    /**
     * 记录可达和最小距离 不存在无限大
     */
    private Map<Object, Integer> distance;

    public GraphNode(T data, Function<T, ?> key) {
        this.key = key;
        this.data = data;
        this.source = new LinkedList<>();
        this.target = new LinkedList<>();
        this.relationships = new ConcurrentHashMap<>();
        this.distance = new HashMap<>();
    }

    public Object getKey() {
        return key.apply(data);
    }
}
