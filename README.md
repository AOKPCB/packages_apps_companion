#AOKPCB Companion
## Current Version
### Beta 2

ROM updater for AOKPCB and potentially other Android 4.X ROMs

Feel free to use this in your ROM, just leave my name in the about dialog

#Integration Guide

So you want to include AOKPCB Companion in your ROM? Super!

Apart from changing images and strings, there are a few things about the ROM you'll need to change too

In the build.prop, you'll need to add the value

	ro.[romname].app.ver=

And then change the String in [Versions.java](https://github.com/cr5315/packages_apps_companion/blob/master/src/com/cr5315/AOKPCB/Versions.java) to match that

AOKPCB Companion checks a file hosted on a server (presumably yours) to see if there is a newer version
Inside the file, you will see this
```
1
http://www.example.com/rom.zip
```

The 1 is how the app checks to see if there is a new version.
If ro.[romname].app.ver=3 and the file has a 5, the app will know there is an update, and continue to get the download link from the second line.
Otherwise, it doesn't bother as it's already on the latest version.

When a user presses the download buttom, the app passes the URL from the second line to DownloadManager, and Android takes care of the rest.