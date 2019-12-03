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
![Example](/doc/example.png)

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
Note: See [Dev-Notes](https://github.com/ingomohr/jira-versions-gadget/wiki/Dev-Notes) for Atlassian developer documentation.

* Open terminal on project folder (the one with the ``pom.xml`` in it.
* Call ``atlas-run`` or ``atlas-debug``(see Atlassian documentation for more).
* This will (re)build and (re)test your plug-in and then start a Jira Server with the plug-in installed.
