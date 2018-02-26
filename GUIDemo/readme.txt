Author: Tim Lindquist (Tim.Lindquist@asu.edu)
        Software Engineering, CIDSE, IAFSE, Arizona State University Polytechnic
Version: July 2015

See http://pooh.poly.asu.edu/Ser321

Purpose: Sample project showing Java and C++ GUI programming. C++ using
the Fulltick (FLTK) library, and Java using swing.
Instructions for installing FLTK can be found at the class web site:
http://pooh.poly.asu.edu/Ser321/Resources/setupFLTK.html

Both Java and C++ programs show an idiom separating the GUI View components
from the Controller code. The controller and view code each residing in
separate classes. The GUI components are defined in a GUI class, and
the controllers specialize the GUI class to provide control functionality.
The GUI classes expose necessary GUI components by defining them to be protected,
thus allowing any extending class to fully access them.

To build the Java program:
ant build.java

You should execute the Java program first with:
java -cp classes ser321.FolderBrowser pathToADirectory
where the path can be either relative or absolute.


The C++ program uses the library package libfltk. Setting up
this library on Debian Wheezy Linux can be done with:
sudo apt-get install libfltk-dev
and is covered on the same page referenced above.
On MacOSX, you can install it using MacPorts with:
sudo port install libfltk-dev

After installing libfltk, to build the C++ program enter:
ant build.cpp
from the command line. This compiles and links the C++ program into
the bin directory. Execute the C++ program from the command line with:
./bin/mediaClient


Depending on your installation of Ant, you may need to place cpp tasks,
and ant-contrib tasks for Ant where it will find them.
This project's lib directory contains the java archive:

antlibs.jar

Copy that jar file to your home directory and extract its contents with:

jar xf antlibs.jar
