package cn.citrus.server;

import cn.hutool.core.util.ObjectUtil;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
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

    }

    /**
     * 解决任意两点最短路
     */
    public void floyd() {
        initDistance();
        // 求source -> target 的最短距离
        // 定义中转  transit
        // if source + transit < target 则更新
        for (GraphNode<T> transit : this.nodesByKey.values()) {
            for (GraphNode<T> source : this.nodesByKey.values()) {
                if (ObjectUtil.equal(source, transit)) {
                    continue;
                }
                for (GraphNode<T> target : this.nodesByKey.values()) {
                    if (ObjectUtil.equal(source, target) || ObjectUtil.equal(transit, target)) {
                        continue;
                    }
                    // source -> transit 到中转不可达
                    if (!source.getDistance().containsKey(transit.getKey())) {
                        continue;
                    }
                    // transit -> target 中转到目标不可达
                    if (!transit.getDistance().containsKey(target.getKey())) {
                        continue;
                    }
                    // 最小化距离
                    Integer sourceTransit = source.getDistance().get(transit.getKey());
                    Integer transitTarget = transit.getDistance().get(target.getKey());
                    if (!source.getDistance().containsKey(target.getKey())) {
                        source.getDistance().put(target.getKey(), sourceTransit + transitTarget);
                        continue;
                    }
                    Integer sourceTarget = source.getDistance().get(target.getKey());
                    log.info("source:{} -> target:{} = {}", source.getKey(), target.getKey(), sourceTarget);
                    source.getDistance().put(target.getKey(), Math.min(sourceTransit + transitTarget, sourceTarget));
                }
            }
        }
    }

    /**
     * 初始化距离数组
     */
    private void initDistance() {
        for (Object key : this.nodesByKey.keySet()) {
            GraphNode<T> source = this.nodesByKey.get(key);
            ConcurrentHashMap<Object, Integer> map = new ConcurrentHashMap<>();
            for (GraphNode<T> target : source.getTarget()) {
                map.put(target.getKey(), 1);
            }
            source.setDistance(map);
        }
    }

    List<Object> reachableNodes(Object searchNodeKey) {
        if (ObjectUtil.isEmpty(searchNodeKey) || !this.nodesByKey.containsKey(searchNodeKey)) {
            log.warn("无效或不存在的节点key: {}", searchNodeKey);
            return new ArrayList<>();
        }
        GraphNode<T> graphNode = this.nodesByKey.get(searchNodeKey);
        Map<Object, Boolean> map = new ConcurrentHashMap<>();
        map.put(searchNodeKey, Boolean.TRUE);
        bfsNextNodes(graphNode, map);
        return map.keySet().stream().toList();
    }

    private void bfsNextNodes(GraphNode<T> graphNode, Map<Object, Boolean> map) {
        if (ObjectUtil.isEmpty(graphNode.getTarget())) {
            return;
        }
        for (GraphNode<T> target : graphNode.getTarget()) {
            if (map.containsKey(target.getKey())) {
                continue;
            }
            map.put(target.getKey(), Boolean.TRUE);

            // 搜索
            bfsNextNodes(target, map);
        }
    }
}
