# Android Easy File Transfer (AEFT)

**Current Versions:**
Desktop: 0.3.0
Android: 2017-07-28-0001

**Android Easy File Transfer (or AEFT)** is an easy way to send files from your Android mobile device to Windows, Linux, or Mac OS X systems running Java 8. While there are other file transfer apps out there, they come loaded with extra software and are packed with ads. AEFT is FOSS, so it will always be free with no ads or extra software.

AEFT Desktop is compatible with ARM, 32 bit, and 64 bit systems. Essentially, it can run on any device that has Java.

The AEFT Android app is compatible with any Android device running Android 4.4 (KitKat) or higher.

**How To Use:**
* Open the latest desktop application.
* Enter the exact path you want the file(s) saved to (don't enter the file name, the device will send this).
* Click Start Server.
* Open the latest version of the Android app.
* Type in your computer's IP address, then click the button to select a file.
* The empty section near the bottom of the screen is a preview window for .jpg and .png files. Tapping on those files will show a preview of them.
* When you have selected a file, press OK.
* Click the Send button. You will get a message saying wether it failed or succeeded. If the progress bar stops partway, it means your computer doesn't have enough RAM to store the temporary file.

If you want to port forward, the app and server use TCP port 25000. If you want to change this, just edit the source files to your liking and recompile them.

**Extra info:**
* The point of me making AEFT was to transfer photos with NO compression at all. This means that sending photos and videos over cellular data uses much more data than most people would like (however if you have unlimited data you're fine).
* AEFT can only send files with a maximum size of 1 Gigabyte. This is due to the maximum length of Java integer arrays.
* Just because AEFT *can* send 1 GB files, it doesn't mean your computer can receive them. Reccomended RAM for large files in 8 GB.
* AEFT **does NOT use encryption!** Do not use it to send sensitive information.
* I have no access at all to anything you send. The connection is directly between YOUR devices.