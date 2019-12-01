package org.ingomohr.jira.versions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.Test;

import com.atlassian.jira.project.Project;
import com.atlassian.jira.project.version.Version;

public class TestVersionRepresentation {

	@Test
	public void testConstructor_HappyDay() {
		Date date = mock(Date.class);
		Project project = mock(Project.class);

		ExpectedProjectData expectedProjectData = new ExpectedProjectData();
		expectedProjectData.project = project;
		expectedProjectData.key = "project-key";
		expectedProjectData.name = "project-name";

		testConstructor(date, project, "1970-01-01", expectedProjectData);
	}

	@Test
	public void testConstructor_NoReleaseDate() {
		Project project = mock(Project.class);

		ExpectedProjectData expectedProjectData = new ExpectedProjectData();
		expectedProjectData.project = project;
		expectedProjectData.key = "project-key";
		expectedProjectData.name = "project-name";

		testConstructor(null, project, "", expectedProjectData);
	}

	@Test
	public void testConstructor_NoReleaseDate_NoProject() {
		testConstructor(null, null, "", ExpectedProjectData.NONE);
	}

	@Test
	public void testConstructor_NoProject() {
		Date date = mock(Date.class);

		testConstructor(date, null, "1970-01-01", ExpectedProjectData.NONE);
	}

	private void testConstructor(Date releaseDate, Project project, String expectedReleaseDate,
			ExpectedProjectData expectedProjectData) {
		Version version = mock(Version.class);
		when(version.getDescription()).thenReturn("version-description");
		when(version.getName()).thenReturn("version-name");
		when(version.getId()).thenReturn(42L);

		when(version.getReleaseDate()).thenReturn(releaseDate);

		if (project != null) {
			when(project.getKey()).thenReturn("project-key");
			when(project.getName()).thenReturn("project-name");
		}
		when(version.getProject()).thenReturn(project);

		VersionRepresentation objUT = new VersionRepresentation(version);
		assertEquals((long) 42, (long) objUT.getId());
		assertEquals("version-description", objUT.getDescription());
		assertEquals("version-name", objUT.getName());
		assertEquals(expectedReleaseDate, objUT.getReleaseDate());
		assertSame(expectedProjectData.project, objUT.getProject());
		assertEquals(expectedProjectData.key, objUT.getProjectKey());
		assertEquals(expectedProjectData.name, objUT.getProjectName());
	}

	private static class ExpectedProjectData {

		public ExpectedProjectData() {

		}

		public ExpectedProjectData(Project project, String name, String key) {
			this.project = project;
			this.name = name;
			this.key = key;
		}

		private Project project;

		private String name;

		private String key;

		private static ExpectedProjectData NONE = new ExpectedProjectData(null, "", "");

	}

}
