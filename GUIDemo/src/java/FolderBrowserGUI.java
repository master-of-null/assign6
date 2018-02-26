package ser321;

import javax.swing.JTree;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.event.TreeSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreeSelectionModel;
import java.net.URL;
import java.io.IOException;
import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JFrame;
import javax.swing.tree.TreePath;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.awt.event.*;
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
 * Purpose: Sample Java Swing view class. FolderBrowserGUI constructs the view components
 * for a sample GUI. This class is extended by the client controller which is
 * the FolderBrowser class. FolderBrowser defines the call-backs for the UI controls.
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
public class FolderBrowserGUI extends JFrame  {

   private static final boolean debugOn = true;

// provide extending (controller) class access to the tree, htmlpane and path
   protected JTree tree;
   protected JEditorPane htmlPane;
   protected String pathToRoot;
   protected JTextField folderTF;
   protected JButton displayButt;
   
   private JSplitPane splitPane;
   private DefaultMutableTreeNode root;
   
   public FolderBrowserGUI(String path) {
      super("Ser321 Folder Browser");
      JPanel jp = new JPanel(new FlowLayout());
      folderTF = new JTextField("pathToFolder",40);
      jp.add(folderTF);
      displayButt = new JButton("Show Folder");
      jp.add(displayButt);
      getContentPane().add("North",jp);
      
      splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);

      //build the tree
      root = new DefaultMutableTreeNode();
      showTreeFrom(path);

      //Create a tree that allows one selection at a time.
      tree = new JTree(root);
      tree.getSelectionModel().setSelectionMode
         (TreeSelectionModel.SINGLE_TREE_SELECTION);
      //Create the scroll pane and add the tree to it. 
      JScrollPane treeView = new JScrollPane(tree);

      //Create the HTML viewing pane.
      htmlPane = new JEditorPane();
      htmlPane.setEditable(false);
      JScrollPane htmlView = new JScrollPane(htmlPane);

      //Add the scroll panes to the split panel.
      splitPane.add(treeView);
      splitPane.add(htmlView);

      Dimension minimumSize = new Dimension(100, 100);
      htmlView.setMinimumSize(minimumSize);
      treeView.setMinimumSize(minimumSize);
      splitPane.setDividerLocation(200);
      treeView.setPreferredSize(new Dimension(100, 100)); 

      splitPane.setPreferredSize(new Dimension(650, 300));
      this.setPreferredSize(new Dimension(650, 300));
      getContentPane().add("Center",splitPane);
      pack();
      setVisible(true);
   }

   protected void showTreeFrom(String path){
      //Clear any subnodes (if they exist)
      clearTree(root);
      //Create the nodes.
      if (path.equals(".")){
         path = System.getProperty("user.dir");
      }
      File aFile = new File(path);
      String rootName = aFile.getName();
      pathToRoot = ".";
      if(path.endsWith(aFile.getName())||path.endsWith(aFile.getName()+"/")){
         pathToRoot = path.substring(
                              0,path.lastIndexOf(aFile.getName()));
      }
      if(pathToRoot.length()==0){
         pathToRoot = ".";
      }
      folderTF.setText(pathToRoot);
      debug("pathToRoot is: "+pathToRoot+ " whose length is: "+
            pathToRoot.length());
      root.setUserObject(aFile.getName());
      if(aFile.isDirectory()){
         File[] listOfFiles = aFile.listFiles();
         for(int i=0; i<listOfFiles.length; i++){
            createNodes(root, listOfFiles[i]);
         }
      }
      if(tree != null){
         DefaultTreeModel model = (DefaultTreeModel)tree.getModel();
         model.nodeStructureChanged(root);
      }
   }

   private void clearTree(DefaultMutableTreeNode root){
      try{
         if(tree !=null){
            DefaultTreeModel model = (DefaultTreeModel)tree.getModel();
            DefaultMutableTreeNode next = null;
            int subs = model.getChildCount(root);
            for(int k=subs-1; k>=0; k--){
               next = (DefaultMutableTreeNode)model.getChild(root,k);
               debug("removing node labelled:"+(String)next.getUserObject());
               model.removeNodeFromParent(next);
            }
         }
      }catch (Exception ex) {
         System.out.println("Exception while trying to clear tree:");
         ex.printStackTrace();
      }
   }

   private void createNodes(DefaultMutableTreeNode top, File aFile) {
      DefaultMutableTreeNode category = null;
      DefaultMutableTreeNode book = null;
      category = new DefaultMutableTreeNode(aFile.getName());
      top.add(category);
      if(aFile.isDirectory()){
         File[] listOfFiles = aFile.listFiles();
         for(int i = 0; i< listOfFiles.length; i++){
            createNodes(category, listOfFiles[i]);
         }
      }
   }

   private void debug(String message) {
      if (debugOn)
         System.out.println("debug: "+message);
   }

}
