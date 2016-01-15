# SimpleTodo

# Pre-work - SimpleTodo

SimpleTodo is an android app that allows building a todo list and basic todo items management functionality including adding new items, editing and deleting an existing item.

Submitted by: Luiza Carneiro

Time spent: 1 additional hour spent for database savings, etc (I think... I did not measure)

## User Stories

The following **required** functionality is completed:

* [x] User can **successfully add and remove items** from the todo list
* [x] User can **tap a todo item in the list and bring up an edit screen for the todo item** and then have any changes to the text reflected in the todo list.
* [x] User can **persist todo items** and retrieve them properly on app restart

The following **optional** features are implemented:

* [x] Persist the todo items [into SQLite](http://guides.codepath.com/android/Persisting-Data-to-the-Device#sqlite) instead of a text file
* [x] Improve style of the todo items in the list [using a custom adapter](http://guides.codepath.com/android/Using-an-ArrayAdapter-with-ListView)
* [x] Add support for completion due dates for todo items (and display within listview item)
* [x] Use a [DialogFragment](http://guides.codepath.com/android/Using-DialogFragment) instead of new Activity for editing items
* [x] Add support for selecting the priority of each todo item (and display in listview item)
* [x] Tweak the style improving the UI / UX, play with colors, images or backgrounds

The following **additional** features are implemented:

* [x] Only update item description if there was a change
* [x] Use DatePicker to enter date of completion
* [x] Support for status
* [x] Splash screen created
* [x] Ordering of items is: first priority; within priority,  due date in ascending order, except dates that are not defined are located at the end of the priority; .
* [x] Icon for application was created (from ldpi to xxxhdpi)
* [x] Splash screen in the beginning.
* [x] Support for notes.
* [x] Cancel button added to edit item
* [x] Depict overdue items within their priority with a more saturated color.


## Video Walkthrough 

Here's a walkthrough of implemented user stories:

[Video Walkthrough](http://i.imgur.com/77WY2jY.gifv)

GIF created with [LiceCap](http://www.cockos.com/licecap/).

## Notes

. Unfortunately, Android Studio started to behave strangely - I use Ubuntu. It would not depict the elements that were either added or deleted to the user interface, change the device, etc. I re-install and things started to wrok again. However, it happened again. Solution: cannot play too much with the designer.

. Because I use Ubuntu, I have to use LiceCap under wine. Took a few minutes to get going...:)

. The suggestion in http://guides.codepath.com/android/ActiveAndroid-Guide#installation to put:
	<activity
            android:name="com.codepath.apps.activities.MainActivity"
            android:noHistory="true"
  is not good! If you set noHistory to true, when you finish your activity, the whole application is 	     finished.

. I really do not like the fact that I need to declare the onClick method for the button from the EditItemFragment in an Activity... Very bizzard. Also, I do not like the fact that in the example to use DialogFragment, and also the code generated by Studio, does not allow to define a method to instantiate the DialogFragment with as many parameters as I want; therefore, we need the createinstance method, and later need to get the parameters the same "weird" way as when dealing with Activity.

. Took some time dealing with bug in active android. The automatic mapping from byte to the database has a problem when bringing the data back to the byte field!

## License

    Copyright [2016] [Luiza Carneiro]

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
