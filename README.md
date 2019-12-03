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

### How to Deploy
Note: See [Dev-Notes](https://github.com/ingomohr/jira-versions-gadget/wiki/Dev-Notes) for Atlassian developer documentation.

* Open terminal on project folder (the one with the ``pom.xml`` in it.
* Call ``atlas-mvn package``
* Open your Jira installation and in it "Manage Apps" in the System Settings.
* Upload the *.jar from the ``/target`` folder
* Create or open a Dashboard and add the gadget "Released Versions".
