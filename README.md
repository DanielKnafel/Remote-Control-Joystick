# Advanced-Programming-2-Remote-Control-Joystick
## Introduction
In this project we developed an android app that controls the FlightGear simulator application, as a remote.

## Video link
https://www.youtube.com/watch?v=gvifnI0PSjM
## Presentation link
https://1drv.ms/p/s!AjmFOzCmeat1qTTKVpNITMDPkWnf
## Explanation of folders and main files structure
Under app/src/main/java/com/example/remotecontroljoystick folder:
*View* - JoystickView - the view of the joystick.

*MainActivity* - The main View of the project.

*utilities* - OnJoystickChange interface.

*viewModel* - contains the MainViewModel.

*Model* - contains the model.

## Preinstallation instructions for Developer
1. Your IDE of choise (we used Android Studio).
2. Flghtgear application.

## Quickstart:
**How to run the program:**
1. Open the FlightGear application and in `settings` under `additional settings` paste the following text:
   `--telnet=socket,in,10,127.0.0.1,6400,tcp`
2. In the FlightGear application press `Fly!`.
3. Under the `Cessna` category click `autostart` to start the engines.
4. Clone the project from GitHub to your pc.
5. Open the project in Android Studio and click 'run'(shift+F10) to run the app on the emulator.
6. In the app in the emulator, enter your local ip address (i.e 192.168.x.x) and port: 6400, then click connect.
7. Now you can control the aircraft with the joystick and the two sliders of the thorttle and the rudder.
8. Have a nice flight!

## Description
In our project we used the MVVM architecture in order to make everything work.

### MVVM

![MVVM](https://raw.githubusercontent.com/DanielKnafel/Advanced-Programming-2-Remote-Control-Joystick/master/Images/mvvm.png)

The MVVM architectural pattern is divided to three parts:
View: responsiple for the visual display of the data to the user.
ViewModel: manages between the View and the Model.
Model: contains the algoritim to proccess the data and send it to the FlightGear server.
We are using Data Binding Between the View and the ViewModel. So when a proprety has been changed in the view (i.e user changed rudder or thorttle), it will trigger the appropriate callback function in the MainViewModel.
The MainViewModel listens to events from the joystick.
The Model gets the information to send to the FlightGear server.

### UML

![UML](https://raw.githubusercontent.com/DanielKnafel/Advanced-Programming-2-Remote-Control-Joystick/master/Images/UML.png)


