/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.trino.sql.planner.planprinter;

import io.airlift.units.Duration;
import io.trino.sql.planner.plan.PlanNode;
import io.trino.sql.planner.plan.PlanNodeId;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

class PlanRepresentation
{
    private final PlanNode root;
    private final Optional<Duration> totalCpuTime;
    private final Optional<Duration> totalScheduledTime;
    private final Optional<Duration> totalBlockedTime;

    private final Map<PlanNodeId, NodeRepresentation> nodeInfo = new HashMap<>();
    // Record the initial plan node info for adaptive plan since it is possible that the plan node id remain the same
    // but the plan node itself changes
    private final Map<PlanNodeId, NodeRepresentation> initialNodeInfo = new HashMap<>();

    public PlanRepresentation(PlanNode root, Optional<Duration> totalCpuTime, Optional<Duration> totalScheduledTime, Optional<Duration> totalBlockedTime)
    {
        this.root = requireNonNull(root, "root is null");
        this.totalCpuTime = requireNonNull(totalCpuTime, "totalCpuTime is null");
        this.totalScheduledTime = requireNonNull(totalScheduledTime, "totalScheduledTime is null");
        this.totalBlockedTime = requireNonNull(totalBlockedTime, "totalBlockedTime is null");
    }

    public NodeRepresentation getRoot()
    {
        return nodeInfo.get(root.getId());
    }

    public Optional<Duration> getTotalCpuTime()
    {
        return totalCpuTime;
    }

    public Optional<Duration> getTotalScheduledTime()
    {
        return totalScheduledTime;
    }

    public Optional<Duration> getTotalBlockedTime()
    {
        return totalBlockedTime;
    }

    public Optional<NodeRepresentation> getNode(PlanNodeId id)
    {
        return Optional.ofNullable(nodeInfo.get(id));
    }

    public Optional<NodeRepresentation> getInitialNode(PlanNodeId id)
    {
        return Optional.ofNullable(initialNodeInfo.get(id));
    }

    public void addNode(NodeRepresentation node)
    {
        nodeInfo.put(node.getId(), node);
    }

    public void addInitialNode(NodeRepresentation node)
    {
        initialNodeInfo.put(node.getId(), node);
    }
}
