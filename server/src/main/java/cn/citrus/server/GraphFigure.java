package cn.citrus.server;

import cn.hutool.core.map.MapUtil;
import lombok.NonNull;
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

    public void init(List<GraphNode<T>> graphNodeList) {
        for (GraphNode<T> graphNode : graphNodeList) {
            this.nodesByKey.put(graphNode.getKey(), graphNode);
            log.info(graphNode.toString());
        }
    }

    public void addRelationShip(@NonNull Object sourceKey, @NonNull Object targetKey) {
        if (!this.nodesByKey.containsKey(sourceKey)) {
            log.warn("当前图中不包含此节点{}", sourceKey);
            return;
        }
        if (!this.nodesByKey.containsKey(targetKey)) {
            log.warn("当前图中不包含此节点{}", targetKey);
            return;
        }

        GraphNode<T> source = this.nodesByKey.get(sourceKey);
        GraphNode<T> target = this.nodesByKey.get(targetKey);

        source.getTarget().add(target);
        target.getSource().add(source);

        // todo 单源最短路



    }

}
