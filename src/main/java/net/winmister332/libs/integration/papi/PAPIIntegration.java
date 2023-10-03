/*
 * ==========
 * PAPIIntegration.java
 * ==========
 * Project: WMLib
 * Developer(s): WinMister332
 * TimeCreated: 2023/09/29 @ 02:28:25 AM
 * License: MIT
 * ------------------
 * Copyright (c) $originalCopyright.match("Copyright \(c\) (\d+)", 1, "-")2023
 * ===========
 *
 */

package net.winmister332.libs.integration.papi;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import net.winmister332.libs.WMLib;
import net.winmister332.libs.utils.PluginInfo;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class PAPIIntegration extends PlaceholderExpansion
{
    public static PAPIIntegration getInstance() { return new PAPIIntegration(); }

    public PAPIIntegration()
    {}

    @Override
    public @NotNull String getIdentifier() {
        return WMLib.getInstance().getPluginMeta().getName();
    }

    @Override
    public @NotNull String getAuthor() {
        return PluginInfo.DEVELOPER;
    }

    @Override
    public @NotNull String getVersion() {
        return PluginInfo.VERSION.toString();
    }

    @Override
    public @Nullable String onRequest(OfflinePlayer player, @NotNull String params) {
        if (params.equalsIgnoreCase("developer"))
            return PluginInfo.DEVELOPER;
        else if (params.equalsIgnoreCase("version"))
            return getVersion().toString();
        else if (params.equalsIgnoreCase("mcversion"))
            return WMLib.getInstance().getServer().getMinecraftVersion();
        else if (params.equalsIgnoreCase("serverver"))
            return WMLib.getInstance().getServer().getVersion();
        else return null;
    }
}
