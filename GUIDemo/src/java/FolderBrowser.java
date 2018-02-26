package ser321;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.event.TreeSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreeSelectionModel;
import javax.swing.JOptionPane;
import java.net.URL;
import java.io.IOException;
import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JFrame;
import javax.swing.tree.TreePath;
import javax.swing.event.TreeSelectionListener;
import java.awt.event.WindowListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;

/**
 * Copyright (c) 2015 Tim Lindquist,
 * Software Engineering,
 * Arizona State University at the Polytechnic campus
 * <p/>
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation version 2
 * of the License.
 * <p/>
 * This program is distributed in the hope that it will be useful,
 * but without any warranty or fitness for a particular purpose.
 * <p/>
 * Please review the GNU General Public License at:
 * http://www.gnu.org/licenses/gpl-2.0.html
 * see also: https://www.gnu.org/licenses/gpl-faq.html
 * so you are aware of the terms and your rights with regard to this software.
 * Or, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301,USA
 * <p/>
 * Purpose: Sample Java Swing controller class. FolderBrowserGUI constructs the view components
 * for a sample GUI. This class is extends the GUI to provide the control functionality.
 * When the user does a tree node selection, this valueChanged is called, but virtue of being a
 * TreeSelectionListener and adding itself as a listerner. FolderBrowser defines the call-backs
 * for the JButton as well.
 * It contains sample control functions that respond to button clicks and tree
 * selects.
 * This software is meant to run on Debian Wheezy Linux
 * <p/>
 * Ser321 Principles of Distributed Software Systems
 * see http://pooh.poly.asu.edu/Ser321
 * @author Tim Lindquist (Tim.Lindquist@asu.edu) CIDSE - Software Engineering,
 *                       IAFSE, ASU at the Polytechnic campus
 * @file    FolderBrowserGUI.java
 * @date    July, 2015
 **/
public class FolderBrowser extends FolderBrowserGUI
                           implements TreeSelectionListener,
                                      ActionListener {

   private static final boolean debugOn = true;

   private URL helpURL;

   public FolderBrowser(String path) {
      super(path);
      tree.addTreeSelectionListener(this);
      WindowListener wl = new WindowAdapter() {
         public void windowClosing(WindowEvent e) {System.exit(0);}
      };
      this.addWindowListener(wl);
      displayButt.addActionListener(this);
      initHelp();
   }

   /**
    * actionPerformed is defined by the ActionListener interface.
    * An object of FolderBrowser registers itself to hear about action events
    * caused by the <b>Button Clicks</b> and <b>Menu selecions (none here)</b>.
    * @param ActionEvent the event object created by the source of the
    * button push (the JButton object.)
    */
   public void actionPerformed(ActionEvent e) {
      try{
         if (e.getActionCommand().equals("Show Folder")){
            debug("Show Folder "+folderTF.getText());
            showTreeFrom(folderTF.getText());
         }
      }catch (Exception ex) {
         JOptionPane.showMessageDialog(this, "Exception: "+ex.getMessage());
      }
   }

   public void valueChanged(TreeSelectionEvent e) {
      DefaultMutableTreeNode node = (DefaultMutableTreeNode)
                                    (e.getPath().getLastPathComponent());
      Object nodeInfo = node.getUserObject();
      debug(nodeInfo.toString());
      if (node.isLeaf()) {
         String pathSeg = (String)node.getUserObject();
         if(pathSeg.toLowerCase().endsWith(".txt") ||
            pathSeg.toLowerCase().endsWith(".h") ||
            pathSeg.toLowerCase().endsWith(".xml") ||
            pathSeg.toLowerCase().endsWith(".java") ||
            pathSeg.toLowerCase().endsWith(".cpp") ||
            pathSeg.toLowerCase().endsWith(".json") ||
            pathSeg.toLowerCase().endsWith(".html") ||
            pathSeg.toLowerCase().endsWith(".hpp")){
            TreePath tPath = e.getPath();
            String path = pathToRoot;
            for (int i=0; i<tPath.getPathCount(); i++){
               DefaultMutableTreeNode dmtn =
                  (DefaultMutableTreeNode)tPath.getPathComponent(i);
               path = path + "/"+(String)(dmtn.getUserObject());
            }
            debug("path is: "+path);
            try{
               File aFile = new File(path);
               String urlStr = "file://"+aFile.getAbsolutePath();
               displayURL(new URL(urlStr));
            }catch (Exception ex){
               System.out.println("error forming url: "+
                                  ex.getMessage());
            }
         }
      } else {
         displayURL(helpURL); 
      }
      debug(nodeInfo.toString());
   }

   private void initHelp() {
      String s = null;
      try {
         s = "file://" 
            + System.getProperty("user.dir")
            + System.getProperty("file.separator")
            + "TreeDemoHelp.html";
         debug("Help URL is " + s);
         helpURL = new URL(s);
         displayURL(helpURL);
      } catch (Exception e) {
         System.err.println("Couldn't create help URL: " + s + " exception: "
                            +e.getMessage());
      }
   }

   private void displayURL(URL url) {
      try {
         htmlPane.setPage(url);
      } catch (IOException e) {
         System.err.println("Attempted to read a bad URL: " + url);
      }
   }
   private void debug(String message) {
      if (debugOn)
         System.out.println("debug: "+message);
   }

   public static void main(String[] args) {
      String path = ".";
      if(args.length >= 1){
         path = args[0];
      }
      System.out.println("syntax: java -cp classes ser321.TreeDemo"+
                         " aPath     Initial path is: "+path);
      new FolderBrowser(path);
   }
}
