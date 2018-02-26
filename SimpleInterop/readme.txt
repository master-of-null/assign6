Author: Tim Lindquist (Tim.Lindquist@asu.edu), ASU Polytechnic, CIDSE, SE
Version: October 2016

See http://pooh.poly.asu.edu/Mobile
See http://pooh.poly.asu.edu/Ser321

Purpose: This project shows how to communicate between programs of different
languages using Stream-Based Socket connections. Byte arrays are written/read
on each side of the protocol with the application usually handling what must be
done to provide conversion of the data (object) to/from a byte array. A very
common approach in stream-based socket programming. The application is a simple
echo server with clients. The Echo Server so that a server thread is created
and allocated to each client. This example has clients written in C++, C-Sharp,
and Java. The server and a third client are written in Java.

The second purpose of this example project is to demonstrate one approach that
can be used to run a processes in the background on either
linux or Mac OS X. This allows you to start up a server on your Raspberry Pi,
then to log off of the RPi and have the server continue to run until the system
goes down, or you log in again to stop the service. The approach shown in this
project directory is preferred unless you want the service to start-
up automatically everytime the system is re-booted. If so, then use the system-specific
mechanism for creating a service. Linux uses the files in /etc/init.d as start-ups for
services.

Building and running the server and terminal clients is done with Ant.

To view the targets that are available in this project type ant (targets is the default
target).
1. ant
   The default target shows all targets supported, and the syntax for invoking programs
   after they are built.
   
2. To build all clients and the server:
   ant build.all

3. ant build.java creates two executable jar files in the lib directory for the Java
   server and client programs. Thus, you could execute the server with:
   java -jar lib/echoserver.jar 8080
      or
   java -cp classes ser321.sockets.ThreadedEchoServer 8080
      or
   ant execute.server

4. after ant build.all, the C-Sharp client can be run with
   mono ./bin/EchoClient.exe localhost 8080

5. after ant build.all, the C++ client can be run with:
   ./bin/cppClient localhost "hello from C++" 8080

6. Running a program in the background, and allowing it to continue after you log off.
   To do this, you should provide execute permission to the shell scripts accompanying
   this example (the jar tool strips them off).
   chmod u+x background startServerBackground.sh
   You will find background and startServerBackground.sh in the project base directory.
   These are shell script command files. When you execute startServerBackground.sh it
   will start the echo server, and record its proces id. The script assigns the standard
   output, error, and input streams (stdin, stderr, and stdout) to files. Run:
   ./startServerBackground.sh
  
   After executing this command script, the echo server will run until you kill it.
   To see its status, and get the information needed to kill it (PID), use the ps command:
   ps
   which will show you a listing of your active programs. The process id (PID) of each
   running program will be shown on the left, and the command that started is shown as
   the last string displayed. To terminate a running program, you can use:
   kill -9 pid
   the -9 is a signal to the process indicating it should not ignore the kill.
   The other included script command file (background) does something similar,
   but provides the ability to start, stop or restart the server. If you execute
   ./background start java -jar lib/echoserver.jar
   The server will be started in the background as with startServerBackground, but
   it places the starting command and the standard input, error, and PID files in the
   log directory. You can use this same script to stop or restart the program.
   ./background stop
   ./background restart

end

