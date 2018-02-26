package ser321.sockets;

import java.net.*;
import java.io.*;
import java.util.*;

/**
 * Copyright 2015 Tim Lindquist,
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * A class for simple client-server connections with a threaded echo server.
 * EchoClient iteratively reads a line of input from the console. It
 * sends the string to the echo server and receives what the server sends
 * back in response. Echo server echo's the string received by the server
 * back to the client.
 *
 * Ser321 Foundations of Distributed Software Systems
 * see http://pooh.poly.asu.edu/Ser321
 * @author Tim Lindquist Tim.Lindquist@asu.edu
 *         Software Engineering, CIDSE, IAFSE, ASU Poly
 * @version August 2015
 */
class EchoClient {
  public static void main (String args[]) {
    Socket sock = null;
    if (args.length != 2) {
      System.out.println("Usage: java ser321.sockets.EchoClient hostName "+
                         "portNumber");
      System.exit(0);
    }
    String host = args[0];
    int portNo = Integer.parseInt(args[1]);
    try {
      sock = new Socket(host, portNo);
      BufferedReader stdin = new BufferedReader(
                                   new InputStreamReader(System.in));
      System.out.print("String to send>");
      String strToSend = stdin.readLine();
      String strReceived;
      OutputStream os = sock.getOutputStream();
      InputStream is = sock.getInputStream();
      int numBytesReceived;
      int bufLen = 1024;
      byte bytesReceived[] = new byte[1024];
      while (!strToSend.equalsIgnoreCase("end")){
         byte bytesToSend[] = strToSend.getBytes();
         os.write(bytesToSend,0,bytesToSend.length);
         numBytesReceived = is.read(bytesReceived,0,bufLen);
         strReceived = new String(bytesReceived,0,numBytesReceived);
         System.out.println("Received from server: "+strReceived);
         System.out.print("String to send>");
         strToSend = stdin.readLine();
      }
      sock.close();
    } catch (Exception e) {e.printStackTrace();}
  }
}
