/*
 * ==========
 * Utilities.java
 * ==========
 * Project: WMLib
 * Developer(s): WinMister332
 * TimeCreated: 2023/09/27 @ 24:18:23 AM
 * License: MIT
 * ------------------
 * Copyright (c) $originalCopyright.match("Copyright \(c\) (\d+)", 1, "-")2023
 * ===========
 *
 */

package net.winmister332.libs.utils;

public final class Utilities
{
    /***
     * Creates a string which only contains letters and numbers or '.', '-', and '_'.
     * @param s
     * @return A string containing safe characters.
     */
    public static String toSafeString(String s) {
        char[] cx = s.toCharArray();
        String sx = "";
        for (int i = 0; i < cx.length; i++) {
            char c = cx[i];
            if (Character.isLetterOrDigit(c) || c == '.' || c == '-' || c == '_')
                sx += c;
            else continue;
        }
        return sx.toLowerCase();
    }

    public static boolean isNullWhiteSpaceOrEmpty(String s)
    {
        return s == null || (s.isEmpty() || s.isBlank());
    }

    public static String removeCharAt(String string, int index, int count)
    {
        String nString = "";
        for (int i = 0; i < string.length(); i++)
        {
            int ix = index + count;
            if (count >= string.length())
                ix = string.length() - 1;
            if (i >= index && i < ix)
                continue;
            nString += string.toCharArray()[i];
        }
        return nString;
    }

    public static String removeStringAt(String sx, int index, int count)
    {
        String s = "";
        for (int i = 0; i < sx.length(); i++)
        {
            int xIndex = index + count;
            if (xIndex > sx.length())
                xIndex = sx.length();
            if (i < xIndex)
                s += sx.toCharArray()[i];
            else continue;
        }
        return s;
    }

    public static String removeStringAt(String sx, int index)
    {
        return removeStringAt(sx, index, sx.length());
    }
}
