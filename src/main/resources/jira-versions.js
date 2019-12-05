// Create minimessage factory
var msg = new gadgets.MiniMessage();
// Show a small loading message to the user
var loadMessage = msg.createStaticMessage("loading...");

// Get configured user prefs
var prefs = new gadgets.Prefs();
var showDate = prefs.getBool("show_date");
var showSummary = prefs.getBool("show_summ");
var numEntries = prefs.getInt("num_entries");

// Fetch issues when the gadget loads
gadgets.util.registerOnLoadHandler(fetchVersions);

function fetchVersions(url) {

	console.log("Fetching versionsx...");

	// // Construct request parameters object
	// var params = {};
	// // Indicate that the response is XML
	// params[gadgets.io.RequestParameters.CONTENT_TYPE] =
	// 	gadgets.io.ContentType.DOM;

	// // Proxy the request through the container server
	// gadgets.io.makeRequest(url, handleResponse, params);

	renderVersions();
	msg.dismissMessage(loadMessage);
	gadgets.window.adjustHeight();
}

/**
 * Handles the response from the server.
 * 
 * @param data the response data to handle
 */
function handleResponse(data) {
	var versions = {};
	renderVersions(versions);

	msg.dismissMessage(loadMessage);
	gadgets.window.adjustHeight();
}

/**
 * Renders the versions to HTML.
 */
function renderVersions(versions) {

	let tr1 = mkTr(mkVersion("1.0.0", "2019-12-05", "description v1"));
	let tr2 = mkTr(mkVersion("0.9.0", "2019-12-04", "description v0.9"));

	let thead = "<thead><th>Name</th><th>Release Date</th><th>Description</th></thead>";
	let tbody = "<tbody>" + tr1 + tr2 + "</tbody>";

	let table = "<table " + mkStyleTable() + ">" + thead + tbody + "</table";
	let html = table;

	document.getElementById('content_div').innerHTML = html;
}

function mkTr(versionRepresentation) {

	let name = versionRepresentation.name;
	let relDate = versionRepresentation.releaseDate;
	let descr = versionRepresentation.description;

	let tr = "<tr>" + mkTd(name) + mkTd(relDate) + mkTd(descr) + "</tr>";
	return tr;
}

function mkStyleTable() {
	return "style=\"width: 100%;font: 12px/18px Arial, Sans-serif;color: #333;background-color: #fff;border-spacing: 0;margin: 10px 0 15px;text-align: left\"";
}

function mkTd(val) {
	return "<td>" + val + "</td>";
}

function mkVersion(name, releaseDate, description) {
	return new VersionRepresentation(name, releaseDate, description);
}

/**
 * Representation for a single fixVersion.
 */
class VersionRepresentation {

	constructor(name, releaseDate, description) {
		this.name = name;
		this.releaseDate = releaseDate;
		this.description = description;
	}

}
