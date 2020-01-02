# jira-versions-gadget
This is a Jira plug-in with a dashboard gadget to list released versions.

### Jira Version
* Tested with Jira Software (Server) 8.5.1

### Features
* Lists the latest released fix versions (of all projects the current user has access to)
* Ordered by release date (newest to oldest)
* Displays maximum 20 versions
* Each version links to the corresponding release hub


### Example
#### Gadget w/o UP
![Example](/doc/example.png)

#### Gadget with User-Preferences Displayed
![Example-with-user-preferences](/doc/example-userpref.png)


### How to Install to Jira
* Open your Jira installation and in it "Manage Apps" in the System Settings.
* Upload the JAR from the release (or from the project's ``/target`` folder - if you built the plug-in locally).
* Create or open a Dashboard and add the gadget "Released Versions".

### How to Deploy
Note: See [Dev-Notes](https://github.com/ingomohr/jira-versions-gadget/wiki/Dev-Notes) for Atlassian developer documentation.

* Open terminal on project folder (the one with the ``pom.xml`` in it.
* Call ``atlas-mvn package``
* This will create the JAR. You just have to upload it to your server.

### How to Develop
#### Start Server
Note: See [Dev-Notes](https://github.com/ingomohr/jira-versions-gadget/wiki/Dev-Notes) for Atlassian developer documentation.

* Open terminal on project folder (the one with the ``pom.xml`` in it.
* Call ``atlas-run`` or ``atlas-debug``(see Atlassian documentation for more).
* This will (re)build and (re)test your plug-in and then start a Jira Server with the plug-in installed.

Note: To stop the server, press CTRL+C (see log on terminal).

#### Hot-Deploy new Build on Running Server
* Open a new terminal while the Jira server is running. (again, open project folder)
* Call ``atlas-mvn package``
* This will rebuild and test your plug-in and redeploy it onto the running Jira server.


#### Admin-User
* The admin user credentials for your test server are "admin"/"admin".


#### How to Change the Test Server Specs
If you change the test-server specs by e.g. adding Jira Service Desk, you need to clean the server data to make your changes come to life.

* Stop the server
* On the project folder call ``atlas-clean``
* Start the server again
