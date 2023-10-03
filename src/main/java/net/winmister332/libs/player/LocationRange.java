/*
 * ==========
 * LocationRange.java
 * ==========
 * Project: WMLib
 * Developer(s): WinMister332
 * TimeCreated: 2023/10/02 @ 02:57:01 AM
 * License: MIT
 * ------------------
 * Copyright (c) $originalCopyright.match("Copyright \(c\) (\d+)", 1, "-")2023
 * ===========
 *
 */

package net.winmister332.libs.player;

import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

/**
 * Handles checking if a player is in range of a specified location within a world.
 */
public class LocationRange
{
    private Location location;
    private int range;

    public LocationRange(Location location, int range)
    {
        this.location = location;
        this.range = range;
    }

    public int getMinX() { return location.getBlockX(); }
    public int getMinY() { return location.getBlockY(); }
    public int getMinZ() { return location.getBlockZ(); }

    public Location getMaxLocationInRange()
    {
        int maxX = getMinX() + range;
        int maxY = getMinY() + range;
        int maxZ = getMinZ() + range;
        return new Location(location.getWorld(), maxX, maxY, maxZ);
    }

    public int getMaxX() { return getMaxLocationInRange().getBlockX(); }
    public int getMaxY() { return getMaxLocationInRange().getBlockY(); }
    public int getMaxZ() { return getMaxLocationInRange().getBlockZ(); }

    public boolean isPlayerInRange(Player player)
    { return isEntityInRange(player); }

    public boolean isEntityInRange(LivingEntity entity)
    {
        Location loc = entity.getLocation();
        int entityX = loc.getBlockX();
        int entityY = loc.getBlockX();
        int entityZ = loc.getBlockZ();

        return !(entityX > getMaxX() && entityX < getMinX()) && !(entityY > getMaxY() && entityY < getMinY()) && !(entityZ > getMaxZ() && entityY < getMinZ());
    }
}
