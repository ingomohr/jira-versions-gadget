package org.ingomohr.jira.versions;

import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.atlassian.jira.permission.ProjectPermissions;
import com.atlassian.jira.project.Project;
import com.atlassian.jira.project.version.Version;
import com.atlassian.jira.security.JiraAuthenticationContext;
import com.atlassian.jira.security.PermissionManager;
import com.atlassian.jira.user.ApplicationUser;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.atlassian.plugins.rest.common.security.AnonymousAllowed;

@Path("/versions")
@Named
public class VersionsResource {

	@ComponentImport
	private PermissionManager permissionManager;
	@ComponentImport
	private JiraAuthenticationContext authenticationContext;

	@Inject
	public VersionsResource(JiraAuthenticationContext authenticationContext, PermissionManager permissionManager) {
		this.authenticationContext = authenticationContext;
		this.permissionManager = permissionManager;
	}

	@GET
	@AnonymousAllowed
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getVersions(@Context HttpServletRequest request) {

		ApplicationUser user = authenticationContext.getLoggedInUser();

		List<Version> versions = getReleasedVersions(user);

		// convert the project objects to ProjectRepresentations
		Collection<VersionRepresentation> preps = new LinkedList<>();
		versions.forEach(version -> preps.add(new VersionRepresentation(version)));
		VersionsRepresentation allVersions = new VersionsRepresentation(preps);

		// return the project representations. JAXB will handle the conversion
		// to XML or JSON.
		return Response.ok(allVersions).build();
	}

	private List<Version> getReleasedVersions(ApplicationUser user) {
		Collection<Project> projects = permissionManager.getProjects(ProjectPermissions.BROWSE_PROJECTS, user);
		List<Version> versions = projects.stream()
				.flatMap(pProject -> pProject.getVersions().stream().filter(pVersion -> pVersion.isReleased()))
				.sorted((v1, v2) -> {
					return v2.getReleaseDate().compareTo(v1.getReleaseDate());
				}).collect(Collectors.toList());

		return versions;
	}

}
