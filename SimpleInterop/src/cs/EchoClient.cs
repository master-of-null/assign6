using System;
using System.IO;
using System.Text;
using System.Net.Sockets;

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
 * Example program showing simple TCP socket connections in C#.NET.
 * EchoClient is a socket client that connects to an echo server
 * which is written in Java.
 *
 * Ser321 Foundations of Distributed Software Systems
 * see http://pooh.poly.asu.edu/Ser321
 * @author Tim Lindquist Tim.Lindquist@asu.edu
 *         Software Engineering, CIDSE, IAFSE, ASU Poly
 * @version August 2015
 */
public class EchoClient {

   public static void Main (string[] args) {
      string hostName = "localhost"; //default host and port
      string portNumber = "2020";
      if(args.Length >= 2){
         hostName = args[0];
         portNumber = args[1];
      }
      try{
         TcpClient tcpc = new TcpClient(hostName, Int32.Parse(portNumber));
         NetworkStream aStream = tcpc.GetStream();
         System.Console.Write("String to send>");
         int rnum;
         string userInput=Console.ReadLine();
         while(!userInput.ToLower().Equals("end")){
            Byte[] sendBytes = Encoding.ASCII.GetBytes(userInput);
            aStream.Write(sendBytes,0,sendBytes.Length);
            aStream.Flush();
            Byte[] receiveBytes = new Byte[sendBytes.Length];
            rnum = aStream.Read(receiveBytes,0,sendBytes.Length);
            Console.WriteLine("Received from server: "
                     +System.Text.ASCIIEncoding.ASCII.GetString(receiveBytes));
            aStream.Flush();
            System.Console.Write("String to send>");
            userInput=Console.ReadLine();
         }
         aStream.Close();
         tcpc.Close();
      } catch (Exception e){
         Console.WriteLine("Socket exception: "+e.ToString());
      }
   }
}
