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
package io.trino.operator.aggregation;

import com.google.common.collect.ImmutableSet;

import java.util.Optional;
import java.util.Set;

import static com.google.common.base.MoreObjects.toStringHelper;
import static java.util.Objects.requireNonNull;

public class AggregationHeader
{
    private final String name;
    private final Set<String> aliases;
    private final Optional<String> description;
    private final boolean decomposable;
    private final boolean orderSensitive;
    private final boolean hidden;
    private final boolean deprecated;

    public AggregationHeader(String name, Set<String> aliases, Optional<String> description, boolean decomposable, boolean orderSensitive, boolean hidden, boolean deprecated)
    {
        this.name = requireNonNull(name, "name cannot be null");
        this.aliases = ImmutableSet.copyOf(aliases);
        this.description = requireNonNull(description, "description cannot be null");
        this.decomposable = decomposable;
        this.orderSensitive = orderSensitive;
        this.hidden = hidden;
        this.deprecated = deprecated;
    }

    public String getName()
    {
        return name;
    }

    public Set<String> getAliases()
    {
        return aliases;
    }

    public Optional<String> getDescription()
    {
        return description;
    }

    public boolean isDecomposable()
    {
        return decomposable;
    }

    public boolean isOrderSensitive()
    {
        return orderSensitive;
    }

    public boolean isHidden()
    {
        return hidden;
    }

    public boolean isDeprecated()
    {
        return deprecated;
    }

    @Override
    public String toString()
    {
        return toStringHelper(this)
                .add("name", name)
                .add("aliases", aliases)
                .add("description", description)
                .add("decomposable", decomposable)
                .add("orderSensitive", orderSensitive)
                .add("hidden", hidden)
                .toString();
    }
}
