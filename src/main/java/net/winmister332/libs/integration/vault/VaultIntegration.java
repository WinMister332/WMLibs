/*
 * ==========
 * VaultIntegration.java
 * ==========
 * Project: WMLib
 * Developer(s): WinMister332
 * TimeCreated: 2023/09/29 @ 02:26:49 AM
 * License: MIT
 * ------------------
 * Copyright (c) $originalCopyright.match("Copyright \(c\) (\d+)", 1, "-")2023
 * ===========
 *
 */

package net.winmister332.libs.integration.vault;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import net.winmister332.libs.WMLib;
import org.bukkit.plugin.RegisteredServiceProvider;

public final class VaultIntegration
{
    private Economy vaultEco;
    private Chat vaultChat;
    private Permission vaultPerms;

    private final WMLib lib = WMLib.getInstance();

    public VaultIntegration()
    {
    }

    private boolean setupEconomy()
    {
        if (lib == null) return false;
        RegisteredServiceProvider<Economy> rsp = lib.getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) return false;
        vaultEco = rsp.getProvider();
        return vaultEco != null;
    }

    private boolean setupChat()
    {
        RegisteredServiceProvider<Chat> rsp = lib.getServer().getServicesManager().getRegistration(Chat.class);
        vaultChat = rsp.getProvider();
        return vaultChat != null;
    }

    private boolean setupPermissions()
    {
        RegisteredServiceProvider<Permission> rsp = lib.getServer().getServicesManager().getRegistration(Permission.class);
        vaultPerms = rsp.getProvider();
        return vaultPerms != null;
    }

    public boolean setup()
    {
        try
        {
            WMLib.getInstance().getWMLogger().logInfo("Setting up Vault integration...");
            if (lib.getServer().getPluginManager().getPlugin("Vault") == null) return false;
            WMLib.getInstance().getWMLogger().logInfo("Setting up Vault economy...");
            setupEconomy();
            WMLib.getInstance().getWMLogger().logInfo("Setting up Vault chat...");
            setupChat();
            WMLib.getInstance().getWMLogger().logInfo("Setting up Vault permissions...");
            setupPermissions();
            return true;
        }
        catch (Exception ex)
        {
            WMLib.getInstance().getWMLogger().logError("Could not setup Vault integration...", ex);
            return false;
        }
    }
}
