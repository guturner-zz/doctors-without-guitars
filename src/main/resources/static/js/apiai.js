$(document).ready(function() {
	$('#cd-search-val').keydown(function(e) {
		if (e.keyCode === 13) {
			askDM();
		}
	});
});

function askDM() {
    var url = '/askDM';
    
    if ($('#cd-search-val').val() != '') {
        url = url + '/?statement=' + $('#cd-search-val').val();
    }
    
	$('#cd-search-response-inner-val').load(url, function(response, status, xhr) {
		if ( status != "error" ) {
			$('#cd-search-response').show();
		}
	});
    	
}

function clearSearchResponse() {
	$('#cd-search-response-inner-val').text('');
	$('#cd-search-response').hide();
}