package cn.citrus.server;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * {@code @author:} wfy
 * {@code @date:} 2022/11/21
 **/
@Slf4j
public class GraphFigure<T> {
    private final Map<Object, GraphNode<T>> nodesByKey = new ConcurrentHashMap<>();
    public GraphNode<T> init(List<GraphNode<T>> graphNodeList) {
        for (GraphNode<T> graphNode : graphNodeList) {
            this.nodesByKey.put(graphNode.getKey(), graphNode);
            log.info(graphNode.toString());
        }
        return null;
    }
}
