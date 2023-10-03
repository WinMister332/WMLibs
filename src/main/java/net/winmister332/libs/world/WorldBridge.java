/*
 * ==========
 * WorldBridge.java
 * ==========
 * Project: WMLib
 * Developer(s): WinMister332
 * TimeCreated: 2023/09/27 @ 13:38:58 PM
 * License: MIT
 * ------------------
 * Copyright (c) $originalCopyright.match("Copyright \(c\) (\d+)", 1, "-")2023
 * ===========
 *
 */

package net.winmister332.libs.world;

import net.winmister332.libs.WMLib;
import net.winmister332.libs.player.LocationRange;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import javax.sound.sampled.FloatControl;

/**
 * This class is a wrapper for worlds.
 */
public final class WorldBridge {
    /**
     * Get all the worlds associated with the server.
     * @return A collection of worlds on the server.
     */
    public static World[] getAllWorlds() {
        return (World[]) WMLib.getInstance().getServer().getWorlds().toArray();
    }

    /**
     * Get the first world which is configured by the server as the main world.
     * @return The main world.
     */
    public static World getMainWorld()
    {
        return WMLib.getInstance().getServer().getWorlds().get(0);
    }

    /**
     * Get a world by the specified name if the world exists.
     * @param worldName The name of the world to check.
     * @return The world with the specified name.
     */
    public static World getWorld(String worldName)
    {
        for (World world : getAllWorlds())
        {
            if (world.getName().equalsIgnoreCase(worldName))
                return world;
        }
        return null;
    }

    /**
     * Checks if the world chunk that the player is in is loaded.
     * @param world The world to check.
     * @param player The player within the world to check.
     * @return True if the chunk is loaded.
     */
    public static boolean isPlayerChunkLoaded(World world, Player player)
    {
        Location playerLoc = player.getLocation();
        return world.isChunkLoaded(playerLoc.getBlockX(), playerLoc.getBlockZ());
    }

    /**
     * Checks if the world spawn chunks are loaded.
     * @param world The world to check.
     * @return True if the spawn chunks are loaded.
     */
    public static boolean isSpawnChunkLoaded(World world)
    {
        Location loc = world.getSpawnLocation();
        return world.isChunkLoaded(loc.getBlockX(), loc.getBlockZ());
    }

    /**
     * Checks if the world spawn chunks and the player chunks are loaded.
     * @param world The world to check.
     * @param player The player to check.
     * @return True if the world is loaded.
     */
    public static boolean isWorldLoaded(World world, Player player)
    {
        return isSpawnChunkLoaded(world) && isPlayerChunkLoaded(world, player);
    }
}
