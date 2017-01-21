 var extent = ol.proj.transformExtent([-179.99999997,-85.04855179,179.97013542,85.05112878],
                                 'EPSG:4326', 'EPSG:3857');
  var center = ol.proj.transform([30, 50],
                                 'EPSG:4326', 'EPSG:3857');
  var view = new ol.View({
    projection: 'EPSG:3857',
    center: center,
    zoom: 3
  });

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
  
  var popup = new ol.Overlay({
      element: document.getElementById('popup')
    });
    map.addOverlay(popup);
  
   map.on('click', function(evt) {
      var element = popup.getElement();
      var coordinate = evt.coordinate;
      var hdms = ol.coordinate.toStringHDMS(ol.proj.transform(
          coordinate, 'EPSG:3857', 'EPSG:4326'));

      $(element).popover('destroy');
      popup.setPosition(coordinate);
      // the keys are quoted to prevent renaming in ADVANCED mode.
      $(element).popover({
        'placement': 'top',
        'animation': false,
        'html': true,
        'content': '<p>The location you clicked was:</p><code>' + hdms + '</code>'
      });
      $(element).popover('show');
    });
   
  var london = ol.proj.fromLonLat([-0.12755, 51.507222]);
  function onClick(id, callback) {
      document.getElementById(id).addEventListener('click', callback);
    }
  onClick('panBtn', function() {
      view.animate({
        center: london,
        zoom: 1,
        duration: 2000
      });
    });