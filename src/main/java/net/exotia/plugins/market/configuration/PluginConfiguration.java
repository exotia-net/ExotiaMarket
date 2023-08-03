package net.exotia.plugins.market.configuration;

import eu.okaeri.configs.OkaeriConfig;
import net.exotia.plugins.market.configuration.sections.DatabaseSection;

public class PluginConfiguration extends OkaeriConfig {
    public DatabaseSection database = new DatabaseSection();
}
