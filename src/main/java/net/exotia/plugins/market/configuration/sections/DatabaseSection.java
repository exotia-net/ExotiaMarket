package net.exotia.plugins.market.configuration.sections;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;

public class DatabaseSection extends OkaeriConfig {
    @Comment({"MySQL database hostname or ip address"})
    public String hostname = "10.10.10.2";
    @Comment({"# MySQL port. default 3306"})
    public int port = 3306;
    @Comment({"# MySQL username"})
    public String username = "exotia_admin";
    @Comment({"# MySQL user password"})
    public String password = "q5KBEHn!8IhbQUCJ";
    @Comment({"# MySQL database name"})
    public String database = "exotia_survival";
}
