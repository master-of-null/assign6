#include "MediaClientGui.cpp"

#include <FL/Fl.H>
#include <FL/Fl_Window.H>
#include <FL/Fl_Button.H>
#include <FL/Fl_Output.H>
#include <FL/Fl_Text_Display.H>
#include <FL/Fl_Text_Buffer.H>
#include <FL/Fl_Input_Choice.H>
#include <FL/Fl_Multiline_Input.H>
#include <FL/Fl_Tree.H>
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
 * Purpose: C++ FLTK client sample.
 * This class extends the Gui component class MediaClientGui.
 * Sample control functions that respond to button clicks and tree item
 * selections.
 * This software is meant to run on Debian Wheezy Linux
 * <p/>
 * Ser321 Principles of Distributed Software Systems
 * see http://pooh.poly.asu.edu/Ser321
 * @author Tim Lindquist (Tim.Lindquist@asu.edu) CIDSE - Software Engineering,
 *                       IAFSE, ASU at the Polytechnic campus
 * @file    MediaClient.cpp
 * @date    July, 2015
 **/
class MediaClient : public MediaClientGui {

   /**
    * ClickedX is one of the callbacks for GUI controls.
    * Callbacks in FLTK need to be static functions. But, static functions
    * cannot directly access instance data. This program uses "userdata"
    * to get around that by passing the instance to the callback
    * function. The callback then accesses whatever GUI control object
    * that it needs for implementing its functionality.
    */
   static void ClickedX(Fl_Widget * w, void * userdata) {
      std::cout << "You clicked Exit" << std::endl;
      exit(1);
   }

   /** 
    * ClickedSayHello is a callback for the Click Me button.
    */
   static void ClickedSayHello(Fl_Widget * w, void * userdata) {
      MediaClient* anInstance = (MediaClient*)userdata;
      Fl_Input* theInputBox = anInstance->aName;
      Fl_Text_Buffer* theBuff = anInstance->buff;
      std::cout << "You clicked the Click Me button with "
                << theInputBox->value()
                << std::endl;
      theBuff->append(" Hello ");
      theBuff->append(theInputBox->value());
      theBuff->append("\n");
      anInstance->show();
   }

   /**
    * TreeCallback is a callback for tree selections, deselections, expand or
    * collapse.
    */
   static void TreeCallback(Fl_Widget *w, void *userdata) {
      MediaClient* anInstance = (MediaClient*)userdata;
      Fl_Tree *tree = (Fl_Tree*)w;
      std::string aStr("unknown");
      switch ( tree->callback_reason() ) {  // reason callback was invoked
      case FL_TREE_REASON_NONE: {aStr = "none"; break;}
        case     FL_TREE_REASON_OPENED: {aStr = "opened";break;}
      case     FL_TREE_REASON_CLOSED: {aStr = "closed"; break;}
      case   FL_TREE_REASON_SELECTED: {aStr = "selected"; break;}
//        case FL_TREE_REASON_RESELECTED: aStr = "opened";
      case FL_TREE_REASON_DESELECTED: {aStr = "deselected"; break;}
//      case FL_TREE_REASON_DRAGGED: {aStr = "dragged"; break;}
      }
      fprintf(stderr, "TreeCallback");
      // Find item that was clicked
      Fl_Tree_Item *item = (Fl_Tree_Item*)tree->item_clicked();
      if ( item ) {
         fprintf(stderr, " item='%s'", item->label());	// print item's label
         //tree->deselect_all();		// deselect all items
         //tree->select(item);		// select this one
         // tree->redraw();
      } else {
         fprintf(stderr, "(NO ITEM?)\n");
      }
      fprintf(stderr, " call back reason: %s\n",aStr.c_str());
   }

   // Static menu callback method
   static void Menu_ClickedS(Fl_Widget*w, void*data) {
      MediaClient *o = (MediaClient*)data;
      o->Menu_Clicked(); //call the instance callback method
   }

   // Menu selection callback that an instance method with access to instance vars.
   void Menu_Clicked() {
      char picked[80];
      menubar->item_pathname(picked, sizeof(picked)-1);
      string selectPath(picked);
      cout << "Selected Menu Path: " << selectPath << endl;
      int select = mediaType->value();
      cout << "Selected media type: " << ((select==0)?"Music":"Video") << endl;
      // Handle menu selections
      string whichHandled("none");
      if(selectPath.compare("File/Save")==0){
         whichHandled = "file-save";
      }else if(selectPath.compare("File/Restore")==0){
         whichHandled = "file-restore";
      }else if(selectPath.compare("File/Tree Refresh")==0){
         whichHandled = "file-tree refresh";
      }else if(selectPath.compare("File/Exit")==0){
         exit(0);
      }else if(selectPath.compare("Media/Add")==0){
         whichHandled = "media-add";
      }else if(selectPath.compare("Media/Remove")==0){
         whichHandled = "media-remove";
      }else if(selectPath.compare("Media/Play")==0){
         whichHandled = "media-play";
      }
      cout << "You handled: " << whichHandled << endl;
   }

public:
   MediaClient(const char * name = 0) : MediaClientGui(name) {
      callback(ClickedX);
      tree->callback(TreeCallback, (void*)this);
      clicked->callback(ClickedSayHello, (void*)this);
      menubar->callback(Menu_ClickedS, (void*)this);
   }
};

int main() {
   MediaClient mc("Ser321 FullTick Example");
   return (Fl::run());
}
