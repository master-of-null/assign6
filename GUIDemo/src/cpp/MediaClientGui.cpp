#include <FL/Fl.H>
#include <FL/Fl_Window.H>
#include <FL/Fl_Button.H>
#include <FL/Fl_Output.H>
#include <FL/Fl_Tree.H>
#include <FL/Fl_Tree_Item.H>
#include <FL/Fl_Menu_Bar.H>
#include <FL/Fl_Choice.H>
#include <FL/Fl_Text_Display.H>
#include <FL/Fl_Text_Buffer.H>
#include <stdio.h>
#include <iostream>
#include <stdlib.h>

using namespace std;

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
 * Purpose: Sample C++ FLTK view class. MediaClientGui constructs the view
 * for media app. This class is extended by the client controller which is
 * the MediaClient class. MediaClient defines the call-backs for UI controls.
 * It contains sample control functions that respond to button clicks and tree
 * selects.
 * This software is meant to run on Debian Wheezy Linux
 * <p/>
 * Ser321 Principles of Distributed Software Systems
 * see http://pooh.poly.asu.edu/Ser321
 * @author Tim Lindquist (Tim.Lindquist@asu.edu) CIDSE - Software Engineering,
 *                       IAFSE, ASU at the Polytechnic campus
 * @file    MediaClientGui.cpp
 * @date    July, 2015
 **/
class MediaClientGui : public Fl_Window {
protected:

   /**
    * tree is the Fl_Tree object that occupies the left side of the window.
    * this tree control provides the ability to add and remove items and to
    * manipulate and query the tree when an exception occurs.
    */
   Fl_Tree * tree;

   /**
    * aName is the Fl_Input object at the top of the right side of the window.
    * Its for the user to enter a message.
    */
   Fl_Input * aName;

   /**
    * clicked is the Fl_Button labeled Click Me.
    * When the user clicks this button, the controller should form a message based on
    * the contents of the aName field and display the resulting message in the
    * text display aLab.
    */
   Fl_Button * clicked;
   
   /**
    * aLab is an Fl_Text_Display that appears below the Click Me button. This label
    * is for displaying a message to the user.
    */
   Fl_Text_Display * aLab;

   Fl_Text_Buffer * buff;
   Fl_Menu_Bar *menubar;
   Fl_Choice *mediaType;

public:
   MediaClientGui(const char * name = "Ser321") : Fl_Window(400,350,name) {
      begin();

      menubar = new Fl_Menu_Bar(0, 0, this->w(), 25);
      menubar->add("File/Save");
      //menubar->add("File/Restore", 0, Menu_CB, (void*)this);
      menubar->add("File/Restore");
      menubar->add("File/Tree Refresh");
      menubar->add("File/Exit");
      menubar->add("Media/Add");
      menubar->add("Media/Remove");
      menubar->add("Media/Play");

      // create a tree control at position x=10, y=10. Its 150 pixels wide
      // and window height less 20 pixels high. Add some sample tree nodes.
      tree = new Fl_Tree(10, 35, 150, this->h()-45);
      tree->add("Flintstones/Fred");
      tree->add("Flintstones/Wilma");
      tree->add("Flintstones/Pebbles");
      tree->add("Simpsons/Homer");
      tree->add("Simpsons/Marge");
      tree->add("Simpsons/Bart");
      tree->add("Simpsons/Lisa");
      tree->close("/Simpsons");

      // add a text input control at x=170, y=10 of width 200 pixels and
      // height of 25 pixels. Initialize it contents to your name.
      aName = new Fl_Input(175, 35, 200, 25);
      aName->value("your name");

      // add a button control kinda centered in right pane and labeled
      // Click Me.
      clicked = new Fl_Button(225, 70, 100, 25, "Click Me");
      buff = new Fl_Text_Buffer(); // a buffer for the label.

      // place a label below the button with enough height to display
      // multiple lines.
      aLab = new Fl_Text_Display(175, 105, 200, 125);
      aLab->buffer(buff);

      // create the media type drop-down (input_choice)
      mediaType = new Fl_Choice(275, 245, 100, 25, "Media Type");
      mediaType->add("Music");
      mediaType->add("Video");
      mediaType->value(0); // set the control initially to Music

      end();
      show();
   }
};
