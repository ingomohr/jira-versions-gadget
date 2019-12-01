package org.ingomohr.jira.versions;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.jcip.annotations.Immutable;

@Immutable
@XmlRootElement
public class VersionsRepresentation {

	@XmlElement
	private List<VersionRepresentation> versions;

	public VersionsRepresentation() {
		this(null);
	}

	public VersionsRepresentation(Iterable<VersionRepresentation> versions) {
		this.versions = new ArrayList<>();
		if (versions != null) {
			for (VersionRepresentation version : versions) {
				this.versions.add(version);
			}
		}
	}

	public List<VersionRepresentation> getVersions() {
		return versions;
	}

	public void setVersions(List<VersionRepresentation> versions) {
		this.versions = versions;
	}

}
