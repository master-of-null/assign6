package ser321.server;

import java.net.*;
import java.io.*;
import java.util.*;
import java.nio.file.*;
import java.nio.charset.*;
import java.text.*;


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
 * A class for client-server connections with a threaded server.
 * The echo server creates a server socket. Once a client arrives, a new
 * thread is created to service all client requests for the connection.
 * The example includes a java client and a C# client. If C# weren't involved,
 * the server and client could use a bufferedreader, which allows readln to be
 * used, and printwriter, which allows println to be used. These avoid
 * playing with byte arrays and encodings. See the Java Tutorial for an
 * example using buffered reader and printwriter.
 *
 * Ser321 Foundations of Distributed Software Systems
 * see http://pooh.poly.asu.edu/Ser321
 * @author Tim Lindquist Tim.Lindquist@asu.edu
 *         Software Engineering, CIDSE, IAFSE, ASU Poly
 * @version August 2015
 */
/**
 * @author Tim Lindquist ASU Polytechnic Department of Engineering
 * @version October 2009
 */
public class ThreadedEchoServer extends Thread {
	private Socket conn;
	private int id;

	public ThreadedEchoServer (Socket sock, int id) {
	 this.conn = sock;
	 this.id = id;
	}

	public void run() {
		try {
			OutputStream outSock = conn.getOutputStream();
			InputStream inSock = conn.getInputStream();
			byte clientInput[] = new byte[1024]; // up to 1024 bytes in a message.
			int numr = inSock.read(clientInput,0,1024);  // make this read from the fileData byte array
			String clientString = new String(clientInput,0,numr);
			String fileStr = "." + clientString.split(" ")[1];

			System.out.println("NUMBER from INSTREAM: " + Integer.toString(numr));

			Path filePath = Paths.get(fileStr);
			String htmlHeader;
			
			if (Files.notExists(filePath) || Files.isDirectory(filePath)) {
				System.out.println("IT DONT EXIST");
				filePath = Paths.get("./www/Ser321/error.html");
				htmlHeader = "HTTP/1.0 404 Not Found\nDate: " + getServerTime() + "\nContent-Type: text/html\nContent-Length: ";
			} else {
				System.out.println("IT DO EXIST");
				htmlHeader = "HTTP/1.0 200 OK\nDate: " + getServerTime() + "\nContent-Type: text/html\nContent-Length: ";
			}

			byte[] fileData = Files.readAllBytes(filePath);
			int bodyLength = fileData.length;

			htmlHeader += Integer.toString(bodyLength) + "\n\n";
			
			byte[] headerBytes = htmlHeader.getBytes();
			int headerLength = headerBytes.length;
			
			// combine header and body byte arrays
			byte[] fullResponse = new byte[headerLength + bodyLength];
			System.arraycopy(headerBytes,0,fullResponse,0         ,headerLength);
			System.arraycopy(fileData,0,fullResponse,headerLength,bodyLength);

			if (numr != -1) {
				System.out.println("read "+numr+" bytes");
				System.out.println("read from client: "+id+" the string: "
										 +clientString);
				outSock.write(fullResponse,0,fullResponse.length);
			}
			inSock.close();
			outSock.close();
			conn.close();
		} catch (IOException e) {
			System.out.println("Can't get I/O for the connection.");
		}
	}

	String getServerTime() {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
		dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
		return dateFormat.format(calendar.getTime());
	}
	 
	public static void main (String args[]) {
	 Socket sock;
	 int id=0;
	 try {
		if (args.length != 1) {
			System.out.println("Usage: java ser321.sockets.ThreadedEchoServer"+
									" [portNum]");
			System.exit(0);
		}
		int portNo = Integer.parseInt(args[0]);
		if (portNo <= 1024) portNo=8888;
		ServerSocket serv = new ServerSocket(portNo);
		while (true) {
			System.out.println("Echo server waiting for connects on port "
									 +portNo);
			sock = serv.accept();
			System.out.println("Echo server connected to client: "+id);
			ThreadedEchoServer myServerThread = new ThreadedEchoServer(sock,id++);
			myServerThread.start();
		}
	 } catch(Exception e) {e.printStackTrace();}
	}
}
