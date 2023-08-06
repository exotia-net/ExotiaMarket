package net.exotia.plugins.market.configuration;

import eu.okaeri.configs.OkaeriConfig;
import net.exotia.plugins.market.configuration.sections.DatabaseSection;

import java.util.Map;

public class PluginConfiguration extends OkaeriConfig {
    public DatabaseSection database = new DatabaseSection();
    public String itemExpireIn = "3d";
    public Map<String, Integer> servicesLimit = Map.of("default", 3, "vip", 6);
}
