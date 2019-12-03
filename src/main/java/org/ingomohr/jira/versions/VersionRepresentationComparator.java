package org.ingomohr.jira.versions;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;
import java.util.Date;

public class VersionRepresentationComparator implements Comparator<VersionRepresentation> {

	@Override
	public int compare(VersionRepresentation o1, VersionRepresentation o2) {
		requireNonNull(o1);
		requireNonNull(o2);

		Date date1 = o1.getReleaseDateObject();
		Date date2 = o2.getReleaseDateObject();

		if (date1 == null && date2 == null) {
			return 0;
		}

		if (date1 == null) {
			return 1;
		}

		if (date2 == null) {
			return -1;
		}

		return date2.compareTo(date1);
	}

}
