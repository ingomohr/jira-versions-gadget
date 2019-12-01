package org.ingomohr.jira.versions;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.atlassian.jira.project.Project;
import com.atlassian.jira.project.version.Version;

public class TestVersionsRepresentationProvider {

	private Project project1;
	private Project project2;

	private VersionsRepresentationProvider objUT;

	@Before
	public void prep() {
		project1 = mock(Project.class, "project1");
		project2 = mock(Project.class, "project2");

		objUT = new VersionsRepresentationProvider();
	}

	@Test
	public void testGet_NoProjects() {
		VersionsRepresentation versions = objUT.getVersionsRepresentation(Collections.emptyList());
		assertEquals(0, versions.getVersions().size());
	}

	@Test
	public void testGet_HappyDay() {

		createVersions(project1, 3, true);
		createVersions(project2, 4, true);

		List<Project> projects = Arrays.asList(project1, project2);
		VersionsRepresentation versions = objUT.getVersionsRepresentation(projects);
		assertEquals(7, versions.getVersions().size());
	}

	@Test
	public void testGet_NotAllVersionsAreReleased() {

		createVersions(project1, 3, false);
		createVersions(project2, 4, true);

		List<Project> projects = Arrays.asList(project1, project2);
		VersionsRepresentation versions = objUT.getVersionsRepresentation(projects);
		assertEquals(4, versions.getVersions().size());
	}

	@Test
	public void testGet_Exactly20ReleasedVersions() {
		createVersions(project1, 10, true);
		createVersions(project2, 10, true);

		List<Project> projects = Arrays.asList(project1, project2);
		VersionsRepresentation versions = objUT.getVersionsRepresentation(projects);
		assertEquals(20, versions.getVersions().size());
	}

	@Test
	public void testGet_MoreThan20ReleasedVersions() {
		createVersions(project1, 10, true);
		createVersions(project2, 13, true);

		List<Project> projects = Arrays.asList(project1, project2);
		VersionsRepresentation versions = objUT.getVersionsRepresentation(projects);
		assertEquals(20, versions.getVersions().size());
	}

	private List<Version> createVersions(Project project, int numOfVersions, boolean released) {

		List<Version> versions = new ArrayList<>();

		for (int i = 0; i < numOfVersions; i++) {
			Version version = mock(Version.class, "v" + i);
			when(version.getProject()).thenReturn(project);
			when(version.isReleased()).thenReturn(released);

			versions.add(version);

		}

		when(project.getVersions()).thenReturn(versions);

		return versions;
	}

}
