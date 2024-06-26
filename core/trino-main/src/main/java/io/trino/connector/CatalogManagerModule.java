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
package io.trino.connector;

import com.google.inject.Binder;
import com.google.inject.Scopes;
import io.airlift.configuration.AbstractConfigurationAwareModule;

import static com.google.inject.multibindings.OptionalBinder.newOptionalBinder;

public class CatalogManagerModule
        extends AbstractConfigurationAwareModule
{
    @Override
    protected void setup(Binder binder)
    {
        binder.bind(DefaultCatalogFactory.class).in(Scopes.SINGLETON);
        binder.bind(LazyCatalogFactory.class).in(Scopes.SINGLETON);
        binder.bind(CatalogFactory.class).to(LazyCatalogFactory.class).in(Scopes.SINGLETON);
        newOptionalBinder(binder, CatalogStoreManager.class);

        CatalogManagerConfig config = buildConfigObject(CatalogManagerConfig.class);
        switch (config.getCatalogMangerKind()) {
            case STATIC -> install(new StaticCatalogManagerModule());
            case DYNAMIC -> install(new DynamicCatalogManagerModule());
        }

        install(new CatalogServiceProviderModule());
    }
}
