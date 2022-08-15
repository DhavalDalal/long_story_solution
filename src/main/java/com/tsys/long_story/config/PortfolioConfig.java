package com.tsys.long_story.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

// This selects the default profile as development
@PropertySource("classpath:application-${spring.profiles.active:development}.properties")
public class PortfolioConfig {

}
