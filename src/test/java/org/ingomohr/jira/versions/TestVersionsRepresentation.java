package org.ingomohr.jira.versions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import org.junit.Test;
import org.junit.internal.matchers.IsCollectionContaining;

public class TestVersionsRepresentation {

	@Test
	public void testConstructor_Default() {
		VersionsRepresentation objUT = new VersionsRepresentation();
		assertEquals(0, objUT.getVersions().size());
	}

	@Test
	public void testConstructor_NoVersions() {
		VersionsRepresentation objUT = new VersionsRepresentation(Collections.emptyList());
		assertEquals(0, objUT.getVersions().size());
	}

	@Test
	public void testConstructor_HappyDay() {
		VersionRepresentation v1 = mock(VersionRepresentation.class, "v1");
		VersionRepresentation v2 = mock(VersionRepresentation.class, "v2");
		VersionRepresentation v3 = mock(VersionRepresentation.class, "v3");

		Collection<VersionRepresentation> versions = Arrays.asList(v1, v2, v3);

		VersionsRepresentation objUT = new VersionsRepresentation(versions);
		assertEquals(3, objUT.getVersions().size());
		assertThat(objUT.getVersions(), IsCollectionContaining.hasItems(v1, v2, v3));
	}

}
