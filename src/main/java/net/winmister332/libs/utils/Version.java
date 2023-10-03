/*
 * ==========
 * Version.java
 * ==========
 * Project: WMLib
 * Developer(s): WinMister332
 * TimeCreated: 2023/09/29 @ 02:31:34 AM
 * License: MIT
 * ------------------
 * Copyright (c) $originalCopyright.match("Copyright \(c\) (\d+)", 1, "-")2023
 * ===========
 *
 */

package net.winmister332.libs.utils;

import net.winmister332.libs.WMLib;

import javax.naming.OperationNotSupportedException;

public final class Version
{
    public static Version getPluginVersion() {
        return Version.parse(WMLib.getInstance().getPluginMeta().getVersion());
    }

    private int[] vers;
    private String tag;

    private boolean isDebugBuild = false;

    public Version(int build, int revision)
    {
        vers = new int[] { build, revision };
    }

    public Version(int minor, int build, int revision)
    {
        vers = new int[] {minor, build, revision};
    }

    public Version(int major, int minor, int build, int revision)
    {
        vers = new int[] { major, minor, build, revision };
    }

    public Version(int build, int revision, String tag)
    {
        this(build, revision);
        this.tag = Utilities.toSafeString(tag);
    }

    public Version(int minor, int build, int revision, String tag)
    {
        this(minor, build, revision);
        this.tag = Utilities.toSafeString(tag);
    }

    public Version(int major, int minor, int build, int revision, String tag)
    {
        this(major, minor, build, revision);
        this.tag = Utilities.toSafeString(tag);
    }

    public int getMajor()
    {
        if (vers.length == 4)
            return vers[0];
        else return 0;
    }

    public int getMinor()
    {
        if (vers.length == 4)
            return vers[1];
        else if (vers.length == 3)
            return vers[0];
        else return 0;
    }

    public int getBuild()
    {
        if (vers.length == 4)
            return vers[2];
        else if (vers.length == 3)
            return vers[1];
        else if (vers.length == 2)
            return vers[0];
        else return 0;
    }

    public int getRevision()
    {
        if (vers.length == 4)
            return vers[3];
        else if (vers.length == 3)
            return vers[2];
        else if (vers.length == 2)
            return vers[1];
        else return 0;
    }

    public String getTag()
    { return getTag(); }

    public int versionCompare(String v1, String v2)
    {
        v1 = stripTagFromVersionString(v1);
        v2 = stripTagFromVersionString(v2);

        String[] v1Str = v1.split(".");
        String[] v2Str = v2.split(".");

        int v1Len = v1Str.length;
        int v2Len = v2Str.length;

        if (v1Len != v2Len)
        {
            int count = Math.abs(v1.length() - v2.length());
            if (v1Len > v2Len)
                for (int i = 1; i <= count; i++)
                    v2 += ".0";
            else
                for (int i = 1; i <= count; i++)
                    v1 += ".0";
        }

        if (v1.equals(v2))
            return 0;
        for (int i = 0; i < v1Str.length; i++)
        {
            String str1 = "", str2 = "";
            for (char c : v1Str[i].toCharArray())
            {
                if (Character.isLetter(c))
                {
                    int u = c - 'a' + 1;
                    if (u < 10)
                        str1 += String.valueOf("0" + u);
                    else
                        str1 += String.valueOf(u);
                }
                else
                    str1+=String.valueOf(c);
            }
            for (char c : v1Str[i].toCharArray())
            {
                if (Character.isLetter(c))
                {
                    int u = c - 'a' + 1;
                    if (u < 10)
                        str1 += String.valueOf("0" + u);
                    else
                        str1 += String.valueOf(u);
                }
                else
                    str1+=String.valueOf(c);
            }

            v1Str[i] = "1" + str1;
            v2Str[i] = '1' + str2;

            int num1 = Integer.parseInt(v1Str[i]);
            int num2 = Integer.parseInt(v2Str[i]);

            if (num1 != num2)
            {
                if (num1 > num2)
                    return 1;
                else
                    return 2;
            }
        }
        return -1;
    }

    public boolean  isVersionNewerThan(Version ver)
    {
        String compareString = ver.getVersionString();
        int x = versionCompare(getVersionString(), compareString);
        return x == 1;
    }

    public boolean isVersionOlderThan(Version ver)
    {
        String compareString = ver.getVersionString();
        int x = versionCompare(getVersionString(), compareString);
        return x == 2;
    }

    public boolean isVersionEqualTo(Version ver)
    {
        String compareString = ver.getVersionString();
        int x = versionCompare(getVersionString(), compareString);
        return x == 0;
    }

    private String getVersionString()
    {
        if (vers.length == 4)
        {
            String major = String.valueOf(vers[0]);
            String minor = String.valueOf(vers[1]);
            String build = String.valueOf(vers[2]);
            String revision = String.valueOf(vers[3]);

            return major + '.' + major + ',' + build + '.' + revision;
        }
        else if (vers.length == 3)
        {
            String minor = String.valueOf(vers[0]);
            String build = String.valueOf(vers[1]);
            String revision = String.valueOf(vers[2]);

            return  minor + '.' + build + '.' + revision;
        }
        else
        {
            String build = String.valueOf(vers[0]);
            String revision = String.valueOf(vers[1]);

            return build + '.' + revision;
        }
    }

    @Override
    public String toString()
    {
        if (Utilities.isNullWhiteSpaceOrEmpty(tag))
            return getVersionString();
        else
            return getVersionString() + '-' + getTag();
    }

    public boolean isDebugBuild() { return isDebugBuild; }

    void setDebug()
    {
        isDebugBuild = true;
    }

    private static String stripTagFromVersionString(String verStr)
    {
        if (verStr.contains("-"))
        {
            int index = verStr.indexOf('-');
            String s = Utilities.removeStringAt(verStr, index);
            return s;
        }
        else return verStr;
    }

    public static Version parse(String verStr)
    {
        if (verStr.contains("-"))
        {
            String[] sx = verStr.split("-");
            String sx1 = sx[0];
            String sx2 = sx[1];
            sx = sx1.split(".");
            if (sx.length == 2)
            {
                String buildStr = sx[0];
                String revisionStr = sx[1];

                int build = Integer.parseInt(buildStr);
                int revision = Integer.parseInt(buildStr);

                return new Version(build, revision, sx2);
            }
            else if (sx.length == 3)
            {
                String minorStr = sx[0];
                String buildStr = sx[1];
                String revisionStr = sx[2];

                int minor = Integer.parseInt(minorStr);
                int build = Integer.parseInt(buildStr);
                int revision = Integer.parseInt(revisionStr);

                return new Version(minor, build, revision, sx2);
            }
            else if (sx.length == 4)
            {
                String majorStr = sx[0];
                String minorStr = sx[1];
                String buildStr = sx[2];
                String revisionStr = sx[3];

                int major = Integer.parseInt(majorStr);
                int minor = Integer.parseInt(minorStr);
                int build = Integer.parseInt(buildStr);
                int revision = Integer.parseInt(revisionStr);

                return new Version(major, minor, build, revision, sx2);
            }
            else
                return null;
        }
        else
        {
            String[] sx = verStr.split(".");
            if (sx.length == 2)
            {
                String buildStr = sx[0];
                String revisionStr = sx[1];

                int build = Integer.parseInt(buildStr);
                int revision = Integer.parseInt(buildStr);

                return new Version(build, revision);
            }
            else if (sx.length == 3)
            {
                String minorStr = sx[0];
                String buildStr = sx[1];
                String revisionStr = sx[2];

                int minor = Integer.parseInt(minorStr);
                int build = Integer.parseInt(buildStr);
                int revision = Integer.parseInt(revisionStr);

                return new Version(minor, build, revision);
            }
            else if (sx.length == 4)
            {
                String majorStr = sx[0];
                String minorStr = sx[1];
                String buildStr = sx[2];
                String revisionStr = sx[3];

                int major = Integer.parseInt(majorStr);
                int minor = Integer.parseInt(minorStr);
                int build = Integer.parseInt(buildStr);
                int revision = Integer.parseInt(revisionStr);

                return new Version(major, minor, build, revision);
            }
            else
                return null;
        }
    }
}
