Panda’s manager
=========

**Panda’s manager** is a twitter based team task manager which help to manage projects. 

The code was tested and used in **Android 2.2** and above. 


Preparing to work
-----

In order to use the application with particular project, first you need to create twitter account for project. This account will be used by all team members. 

Setting up the application
-----
After you have the project’s twitter account you can open the application. The first screen you see is the login screen which is provided by Twitter. You need to enter project account’s username and password to log into **Panda’s manager**. 
![ScreenShot](https://raw.github.com/PROMAND/WhistlingPandas/master/res/screenshot/login.png)

After that, you are brought back to the application, to the settings page. If this is the first time the project is being set up, you will be able to enter project’s name. If not, the name will be already there. 

![ScreenShot](https://raw.github.com/PROMAND/WhistlingPandas/master/res/screenshot/settings.png)

Each user enters his name to start using the program. Otherwise he doesn’t have an access to the application’s main screen. 
![ScreenShot](https://raw.github.com/PROMAND/WhistlingPandas/master/res/screenshot/settings_change.png)

You can choose how frequently you want task list to update automatically (time is set in seconds). 
 


Workflow
-----

### Main screen ###

Main application screen is the task list screen.

![ScreenShot](https://raw.github.com/PROMAND/WhistlingPandas/master/res/screenshot/task_list.png)

It contains timeline with all tasks. Timeline consists of task elements. Each element contains almost all information about the task – title, deadline, assignee, priority which is marked by colour (red – high, yellow – medium, green - low). Icon at the left indicates type of a task, in our case there are two types – a bug or a feature. Tasks are grouped by state. There are four states – assigned (created), in progress (started by someone), finished and rejected.   
In the menu you can choose to see only your tasks (which are assigned to you) or tasks that you assigned to someone. The main screen looks the same, only with filtered tasks.

![ScreenShot](https://raw.github.com/PROMAND/WhistlingPandas/master/res/screenshot/task_options.png)

In the menu you could choose options if you want to change settings or to send invitation to the new project member.

### Add or edit task ###
Choose “Add task” via menu to add new task or click on the existing task to edit it. In the first case you will see this screen:
![ScreenShot](https://raw.github.com/PROMAND/WhistlingPandas/master/res/screenshot/new_task.png)

“Task title” is limited by 30 characters.
“Creator” field is disabled – if you create the task, application simply takes your name from the settings, in order to omit typos in creator’s name. 
In “Assignee” list there will be all team members who participate in project. There will be visible names they entered while starting work with the application. 
“Description” is limited by 40 characters. 
In case you edit a task, all fields will be filled by chosen task information. 

### Send the invitation ###
To send the invitation, choose Options --> Send invite in the menu. You will see the following screen:
![ScreenShot](https://raw.github.com/PROMAND/WhistlingPandas/master/res/screenshot/invite.png)

To send invite just write new member’s email and click “Send” button. Text, which has project credentials, is predefined but you can change it.

### Change settings ###
Settings screen which appears at the start is accessible through the menu: Options --> Settings. If you enter Settings second time you cannot change project name or your name. But other settings, such as update time, wi-fi settings and notifications, could be changed. Wi-fi and notifications setting are not implemented at the moment.


