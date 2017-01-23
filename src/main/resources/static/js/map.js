var extent = ol.proj.transformExtent([-179.99999997,-85.04855179,179.97013542,85.05112878], 'EPSG:4326', 'EPSG:3857');
var center = ol.proj.transform([30, 50], 'EPSG:4326', 'EPSG:3857');

var view = new ol.View({
	projection: 'EPSG:3857',
	center: center,
	zoom: 3
});

// Code for custom tiles:
var overlay = new ol.layer.Tile({
    source: new ol.source.XYZ({
      urls:[
        'img/map/{z}/{x}/{y}.png'
      ],
      extent: extent,
      minZoom: 1,
      maxZoom: 3,
      tilePixelRatio: 1.000000
    })
});

var map = new ol.Map({
    layers: [
      new ol.layer.Tile({
        source: new ol.source.OSM()
      }),
      overlay
    ],
    renderer: 'canvas',
    target: 'map',
    view: view
});
  
// Popup info for each landmark:
var popup = new ol.Overlay({
	element: document.getElementById('popup')
});
map.addOverlay(popup);
   
// When a landmark is selected, populate the table with relevant info and set an onclick event for the 'GO' button:
function setWaypointEvent(name, description, lat, long) {
	var element = popup.getElement();
	
	// Start fresh:
	removeDescription();
	removeWaypointEvent();
	removeOverlay();
	
	// This is the default "none selected" value:
	if (name == '- - -') {
		return;
	}
	
	var waypoint = ol.proj.fromLonLat([long, lat]);
	
	setDescription(description);
	
	onClick('panBtn', function() {
		view.animate({
	        center: waypoint,
	        zoom: 3,
	        duration: 2000
	    });
		setTimeout(function(){
			setOverlay(name, description, waypoint);
			$(element).popover('show');
		}, 2000);
		
	});
}

function removeWaypointEvent() {
	var el = document.getElementById('panBtn'),
    elClone = el.cloneNode(true);

	el.parentNode.replaceChild(elClone, el);
}

function setDescription(description) {
	$('#description').html(description);
}

function removeDescription() {
	$('#description').html('');
}

function setOverlay(name, description, waypoint) {
	var element = popup.getElement();
	
	$(element).popover('destroy');
	popup.setPosition(waypoint);
      
	var hdms = ol.coordinate.toStringHDMS(ol.proj.transform(
			waypoint, 'EPSG:3857', 'EPSG:4326'));
      
	$(element).attr('title', name);
      
	// The keys are quoted to prevent renaming in ADVANCED mode.
	$(element).popover({
        'placement': 'top',
        'animation': false,
        'html': true,
        'content': description
	});
}

function removeOverlay() {
	var element = popup.getElement();
	$(element).popover('destroy');
}

// Wrapper for easily adding onclick events:
function onClick(id, callback) {
	document.getElementById(id).addEventListener('click', callback);
}

// Bootstrap-Select hides the actual "select" element, this allows triggering "click" events on "select" options:
$('.selectpicker').on('changed.bs.select', function (e, clickedIndex, newValue, oldValue) {
    var selected = $(e.currentTarget).val();
    $("#drpDown option[value='" + selected + "']").trigger("click");
});

// Fix for Bootstrap-Select links (mainly for apostrophes):
var i;
var namesToFix = ["Falcon&apos;s Hollow"];
for (i = 0; i < namesToFix.length; i++) {
	$("span:contains('" + namesToFix[i] + "')").html(namesToFix[i]);
	$("option:contains('" + namesToFix[i] + "')").html(namesToFix[i]);
}