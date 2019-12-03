package org.ingomohr.jira.versions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.hamcrest.Matchers;
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

	@Test
	public void testGet_Sort() throws Exception {

		Version v0 = createVersion("0", null);
		Version v1 = createVersion("1", "2018-01-12");
		Version v2 = createVersion("2", "2019-02-10");
		Version v3 = createVersion("3", "2019-12-01");

		Project p1 = mock(Project.class);
		when(p1.getVersions()).thenReturn(Arrays.asList(v2, v0));

		Project p2 = mock(Project.class);
		when(p2.getVersions()).thenReturn(Arrays.asList(v1, v3));

		List<Project> projects = Arrays.asList(p1, p2);
		VersionsRepresentation versions = objUT.getVersionsRepresentation(projects);
		List<VersionRepresentation> versionEntries = versions.getVersions();
		List<String> versionNames = versionEntries.stream().map(v -> v.getName()).collect(Collectors.toList());

		String v0Name = v0.getName();
		String v1Name = v1.getName();
		String v2Name = v2.getName();
		String v3Name = v3.getName();
		assertThat(versionNames, Matchers.contains(v3Name, v2Name, v1Name, v0Name));
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

	private Version createVersionForDate(String name, Date releaseDate) {
		Version version = mock(Version.class);
		when(version.isReleased()).thenReturn(true);
		when(version.getName()).thenReturn(name);
		when(version.getReleaseDate()).thenReturn(releaseDate);
		return version;
	}

	private Version createVersion(String name, String releaseDate) throws Exception {
		Date date = null;
		if (releaseDate != null) {
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			date = format.parse(releaseDate);
		}
		return createVersionForDate(name, date);
	}

}
