# ARC Thunder 2016-2017 Season
The repository containing code for ARC Thunder's 2016 season, primarily on the FTC challenge Velocity Vortex.

Whenever creating or editing code for the robot, please use the .xml file in this repository for robot configuration settings.

Remember, only one person edits or changes code at one time. Also, update code on GitHub after you are finished changing it.

## Useful Resources:
[Andover Robotics Club Website] (http://www.andoverrobotics.com/home)
  - The ARC website includes the club calendar with meeting dates and times.
  
[ARC Thunder 2016-2017 Online Documentation] (https://drive.google.com/drive/folders/0B1Z4MGrlsAEJVl9OYzVlSEJicUk?usp=sharing)
  - This folder includes pictures of ARC Thunder's progress and the file that contains documentation.
  - The folder is not accessible to everyone, contact a board member to ask for editing access.
  
[FTC Android Studio Project] (https://github.com/ftctechnh/ftc_app)
  - This is FTC's Android Studio Project for creating the FTC Robot Controller app. 
  
### Other Resources:
[FTC - Java Programming] (http://paws.kettering.edu/~webe3546/FTCJavaProgramming.pdf)
  - This gives a basic rundown of how to use Android Studio in coding for the robot.
  - This presentation is made for to last year's FTC app (2015-2016), so some of the code might be outdated. 
  
### Wireless Debugging in Android Studio using ADB
[Video Resource] (https://www.youtube.com/watch?v=YtZ55JabfPc)

[pg 44-47, Getting Started with Android Studio] (http://ftc.flfirst.org/images/Home_Files/Documents/2016/2016_SDK_Changes.pdf)


Prerequisites: Android Studio, ADB

1.) Connect Robot Controller phone through USB

2.) Open up command prompt

3.) Set the TCPIP mode to 5555 using "adb tcpip 5555"

4.) Connect the phone to port 5555 using "adb connect device-ip:5555", where device-ip is your device's IP adress

5.) Done


To disconnect, use "adb -s device-ip:5555 usb"
