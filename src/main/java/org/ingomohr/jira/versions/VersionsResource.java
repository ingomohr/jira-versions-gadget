package org.ingomohr.jira.versions;

import java.util.Collection;

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

		Collection<Project> projects = permissionManager.getProjects(ProjectPermissions.BROWSE_PROJECTS, user);

		VersionsRepresentation rep = new VersionsRepresentationProvider().getVersionsRepresentation(projects);

		// return the project representations. JAXB will handle the conversion
		// to XML or JSON.
		return Response.ok(rep).build();
	}

}
