package cn.citrus.server;

import lombok.*;

import java.util.LinkedList;
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
public class GraphNode<T> {
    private T data;
    private Function<T, ?> key;
    /**
     * 入边
     */
    private LinkedList<GraphNode<T>> source;
    /**
     * 出边
     */
    private LinkedList<GraphNode<T>> target;
    /**
     * 保存节点关系
     */
    private Map<String, GraphNode<T>> relationships;
    /**
     * 记录可达和最小距离
     */
    private Map<String, String> distance;

    public GraphNode(T data, Function<T, ?> key) {
        this.key = key;
        this.data = data;
    }

    public Object getKey() {
        return key.apply(data);
    }
}
