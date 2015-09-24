/*
 * Copyright 2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.cloud.bootstrap.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.SearchStrategy;
import org.springframework.boot.context.properties.ConfigurationBeanFactoryMetaData;
import org.springframework.boot.context.properties.ConfigurationPropertiesBindingPostProcessorRegistrar;
import org.springframework.cloud.context.properties.ConfigurationPropertiesBeans;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Dave Syer
 *
 */
@Configuration
public class ConfigurationPropertiesBootstrapConfiguration {

	@Bean
	@ConditionalOnMissingBean(search = SearchStrategy.CURRENT)
	public ConfigurationPropertiesBeans configurationPropertiesBeans(
			ApplicationContext context) {
		// Since this is a BeanPostProcessor we have to be super careful not to
		// cause a cascade of bean instantiation. Knowing the *name* of the beans we
		// need is super optimal, but a little brittle (unfortunately we have no
		// choice).
		ConfigurationBeanFactoryMetaData metaData = context.getBean(
				ConfigurationPropertiesBindingPostProcessorRegistrar.BINDER_BEAN_NAME
				+ ".store", ConfigurationBeanFactoryMetaData.class);
		ConfigurationPropertiesBeans rebinder = new ConfigurationPropertiesBeans();
		rebinder.setBeanMetaDataStore(metaData);
		return rebinder;
	}


}