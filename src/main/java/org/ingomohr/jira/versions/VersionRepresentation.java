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
@SuppressWarnings("UnusedDeclaration")
@XmlRootElement
public class VersionRepresentation {

	@XmlElement
	private String name;

	@XmlElement
	private String description;

	@XmlElement
	private String releaseDate;

	@XmlElement
	private Project project;

	@XmlElement
	private Long id;

	@XmlElement
	private String projectKey;

	public VersionRepresentation() {

	}

	public VersionRepresentation(Version version) {
		this.name = version.getName();
		this.description = version.getDescription();
		
		Date date = version.getReleaseDate();
		if (date != null) {
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			releaseDate = dateFormat.format(date);
		} else {
			releaseDate = "-";
		}
		
		this.project = version.getProject();

		projectKey = project.getKey();

		id = version.getId();
	}

}