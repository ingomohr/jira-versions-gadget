package org.ingomohr.jira.versions;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import com.atlassian.jira.project.Project;
import com.atlassian.jira.project.version.Version;

/**
 * Provides access to information on released versions.
 */
public class VersionsRepresentationProvider {

	/**
	 * Returns the {@link VersionsRepresentation} containing information on all
	 * released versions in the given projects.
	 * 
	 * @param projects the projects. Cannot be <code>null</code>.
	 * @return versions representation. Never <code>null</code>.
	 */
	public VersionsRepresentation getVersionsRepresentation(Collection<Project> projects) {
		requireNonNull(projects);

		List<Version> versions = getReleasedVersions(projects);

		if (versions.size() > 20) {
			versions = versions.subList(0, 20);
		}

		Collection<VersionRepresentation> versionReps = new LinkedList<>();
		versions.forEach(version -> versionReps.add(new VersionRepresentation(version)));
		VersionsRepresentation allVersions = new VersionsRepresentation(versionReps);

		return allVersions;
	}

	private List<Version> getReleasedVersions(Collection<Project> projects) {
		List<Version> versions = projects.stream()
				.flatMap(pProject -> pProject.getVersions().stream())
				.filter(pVersion -> pVersion.isReleased())
				.collect(Collectors.toList());
		return versions;
	}

}
