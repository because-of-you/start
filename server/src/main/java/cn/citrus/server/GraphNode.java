package cn.citrus.server;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedList;
import java.util.Map;

/**
 * {@code @author:} wfy
 * {@code @date:} 2022/11/21
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GraphNode {
    /**
     * 当前自己
     */
    private final GraphNode current = this;
    /**
     * 入边
     */
    private LinkedList<GraphNode> source;
    /**
     * 出边
     */
    private LinkedList<GraphNode> target;
    /**
     * 保存节点关系
     */
    private Map<String, GraphNode> relationships;
    /**
     * 记录可达和最小距离
     */
    private Map<String, String> distance;
}
