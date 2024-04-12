package org.kauazs.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;

public class LocationUtils {
    public static String getLocation(Location location) {
        return location.getX()+","+location.getY()+","+location.getZ()+","+location.getYaw()+","+location.getPitch()+","+location.getWorld().getName();
    }
    public static Location getFromLocation(String local) {
        double x = Double.parseDouble(local.split(",")[0]);
        double y = Double.parseDouble(local.split(",")[1]);
        double z = Double.parseDouble(local.split(",")[2]);
        float yaw = Float.parseFloat(local.split(",")[3]);
        float pitch = Float.parseFloat(local.split(",")[4]);
        String worldName = local.split(",")[5];

        World world = Bukkit.getWorld(worldName.trim());
        return new Location(world, x, y, z, yaw, pitch);
    }
}
