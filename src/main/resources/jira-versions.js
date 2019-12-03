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
// gadgets.util.registerOnLoadHandler(fetchVersions);

function fetchVersions() {

	console.log("Fetching versions...");

	// Construct request parameters object
	var params = {};
	// Indicate that the response is XML
	params[gadgets.io.RequestParameters.CONTENT_TYPE] =
		gadgets.io.ContentType.DOM;

	// Proxy the request through the container server
	gadgets.io.makeRequest(url, handleResponse, params);
}

function handleResponse(obj) {
	// obj.data contains a Document DOM element
	// parsed from the XML that was requested
	var domData = obj.data;

	console.log("Found DOM data: " + domData);
	
	var children = Array.prototype.slice.call(domData.childNodes);
	console.log("Found DOM data children: " + children.length);
	
	children.forEach(function(child){
		console.log("DomData name: " + child.msg);
	});
	

	// Process the DOM data into a JavaScript object
	var versions = {
		title: getTitle(domData),
		items: getItems(domData)
	};
	renderVersions(versions);

	msg.dismissMessage(loadMessage);
	gadgets.window.adjustHeight();
}

function printChild(child) {
	console.log("Printing child: " + child);
	
	
}

function getTitle(domData) {
	// Return the feed title
	// This function just grabs the first element named "title"
	var titles = domData.getElementsByTagName("title");
	return titles.item(0).firstChild.nodeValue;
}

function getItems(domData) {

	// Items to return
	var items = [];
	// Get a list of the <item> element nodes in the file
	var itemNodes = domData.getElementsByTagName("item");
	// Loop through all <item> nodes
	for (var i = 0; i < itemNodes.length && i < numEntries; i++) {
		var item = {};

		// For each <item> node, get child nodes.
		var childNodes = itemNodes.item(i).childNodes;
		// Loop through child nodes.
		for (var j = 0; j < childNodes.length; j++) {
			// Extract data from title, description, link,
			// and updated date child nodes
			var childNode = childNodes.item(j);
			if (!isElement(childNode) || !childNode.firstChild) {
				continue;
			}
			switch (childNode.nodeName) {
				case "title":
					item.name = childNode.firstChild.nodeValue;
					break;
				case "description":
					item.desc = childNode.firstChild.nodeValue;
					break;
				case "link":
					item.link = childNode.firstChild.nodeValue;
					break;
				case "updated":
					item.date = childNode.firstChild.nodeValue;
					break;
			}
		}
		items.push(item);
	}
	return items;
}

function isElement(node) {
	return node.nodeType == 1;
}

function renderVersions(versions) {
	var html =
		"<div class='title'>" +
		versions.title +
		"</div>";
	for (var i = 0; i < versions.items.length; i++) {
		var item = versions.items[i];
		html +=
			"<div class='jira-item'>" +
			"<a target='_blank' href='" + item.link + "'>" +
			item.name +
			"</a>";
		if (showDate) {
			html +=
				"<div class='jira-item-date'>" +
				item.date +
				"</div>";
		}
		if (showSummary) {
			html +=
				"<div class='jira-item-desc'>" +
				item.desc +
				"</div>";
		}
		html += "</div>";
	}

	document.getElementById('content_div').innerHTML = html;
}
