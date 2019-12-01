package org.ingomohr.jira.versions;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.atlassian.jira.project.Project;
import com.atlassian.jira.project.version.Version;

import net.jcip.annotations.Immutable;

@Immutable
@XmlRootElement
public class VersionRepresentation {

	@XmlElement
	private Long id;

	@XmlElement
	private String name;

	@XmlElement
	private String description;

	@XmlElement
	private String releaseDate;

	@XmlElement
	private Project project;

	@XmlElement
	private String projectKey;

	@XmlElement
	private String projectName;

	public VersionRepresentation() {

	}

	public VersionRepresentation(Version version) {
		this.name = version.getName();
		this.description = version.getDescription();
		id = version.getId();

		Date date = version.getReleaseDate();
		if (date != null) {
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			releaseDate = dateFormat.format(date);
		} else {
			releaseDate = "";
		}

		this.project = version.getProject();

		projectKey = project != null ? project.getKey() : "";

		projectName = project != null ? project.getName() : "";

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public String getProjectKey() {
		return projectKey;
	}

	public void setProjectKey(String projectKey) {
		this.projectKey = projectKey;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	@Override
	public String toString() {
		return "VersionRepresentation [id=" + id + ", name=" + name + ", description=" + description + ", releaseDate="
				+ releaseDate + ", project=" + project + ", projectKey=" + projectKey + ", projectName=" + projectName
				+ "]";
	}

}