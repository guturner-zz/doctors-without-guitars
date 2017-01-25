package org.guy.rpg.dwg.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.stereotype.Component;

@Component
public class TomcatContainerCustomizer implements EmbeddedServletContainerCustomizer {

	private static final Logger LOGGER = LoggerFactory.getLogger(TomcatContainerCustomizer.class);

	@Override
	public void customize(final ConfigurableEmbeddedServletContainer container) {
		if (container instanceof TomcatEmbeddedServletContainerFactory) {
			final TomcatEmbeddedServletContainerFactory tomcat = (TomcatEmbeddedServletContainerFactory) container;
			tomcat.addConnectorCustomizers(connector -> {
				connector.setScheme("https");
				connector.setProxyPort(443);
			});
			LOGGER.info("Enabled secure scheme (https).");
		} else {
			LOGGER.warn("Could not change protocol scheme because Tomcat is not used as servlet container.");
		}
	}
}
