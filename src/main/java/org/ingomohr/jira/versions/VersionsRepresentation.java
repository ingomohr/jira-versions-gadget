package org.ingomohr.jira.versions;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.jcip.annotations.Immutable;

@Immutable
@SuppressWarnings("UnusedDeclaration")
@XmlRootElement
public class VersionsRepresentation {

	@XmlElement
	private List<VersionRepresentation> versions;

	public VersionsRepresentation() {
		versions = null;
	}

	public VersionsRepresentation(Iterable<VersionRepresentation> versions) {
		this.versions = new ArrayList<>();
		for (VersionRepresentation version : versions) {
			this.versions.add(version);
		}
	}
}
