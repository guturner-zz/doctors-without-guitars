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
        url = url + '/?statement=' + encodeURIComponent($('#cd-search-val').val());
    }
    
	$('#cd-search-response-inner-val').load(url, function(response, status, xhr) {
		if ( status != "error" ) {
			$('#cd-search-response-inner-val').hide();
			$('#loading').show();
			$('#cd-search-response').show();
			setTimeout(function(){
				$('#cd-search-response-inner-val').show();
				$('#loading').hide();
			}, 1000);
		}
	});
    	
}

function clearSearchResponse() {
	$('#cd-search-response-inner-val').text('');
	$('#loading').hide();
	$('#cd-search-response').hide();
}