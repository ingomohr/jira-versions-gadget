package org.ingomohr.jira.versions;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

public class TestVersionRepresentationComparator {

	private VersionRepresentationComparator objUT;

	@Before
	public void prep() {
		objUT = new VersionRepresentationComparator();
	}

	@Test
	public void test() throws Exception {
		VersionRepresentation v0 = createVersion(null);
		VersionRepresentation v1 = createVersion("2018-01-01");
		VersionRepresentation v2 = createVersion("2019-01-01");
		VersionRepresentation v3 = createVersion("2019-12-01");
		VersionRepresentation v4 = createVersion("2019-12-31");

		compareAndVerify(v0, v0, 0);
		compareAndVerify(v1, v1, 0);
		compareAndVerify(v2, v2, 0);
		compareAndVerify(v3, v3, 0);
		compareAndVerify(v4, v4, 0);

		compareAndVerify(v0, v1, 1);
		compareAndVerify(v0, v2, 1);
		compareAndVerify(v0, v3, 1);
		compareAndVerify(v0, v4, 1);

		compareAndVerify(v1, v0, -1);
		compareAndVerify(v1, v2, 1);
		compareAndVerify(v1, v3, 1);
		compareAndVerify(v1, v4, 1);

		compareAndVerify(v2, v0, -1);
		compareAndVerify(v2, v1, -1);
		compareAndVerify(v2, v3, 1);
		compareAndVerify(v2, v4, 1);

		compareAndVerify(v3, v0, -1);
		compareAndVerify(v3, v1, -1);
		compareAndVerify(v3, v2, -1);
		compareAndVerify(v3, v4, 1);

		compareAndVerify(v4, v0, -1);
		compareAndVerify(v4, v1, -1);
		compareAndVerify(v4, v2, -1);
		compareAndVerify(v4, v3, -1);

	}

	private void compareAndVerify(VersionRepresentation v1, VersionRepresentation v2, int expectedResult) {
		assertEquals(expectedResult, objUT.compare(v1, v2));
	}

	private VersionRepresentation createVersion(String releaseDate) throws Exception {
		Date date = null;
		if (releaseDate != null) {
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			date = format.parse(releaseDate);
		}
		return createVersion(date, releaseDate);
	}

	private VersionRepresentation createVersion(Date releaseDate, String releaseDateAsString) {
		VersionRepresentation version = mock(VersionRepresentation.class);
		when(version.getReleaseDate()).thenReturn(releaseDateAsString);
		when(version.getReleaseDateObject()).thenReturn(releaseDate);
		return version;
	}

}
