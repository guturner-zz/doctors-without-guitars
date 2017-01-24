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
    
    $('#cd-search-response').load(url);
}

function clearSearchResponse() {
	$('#cd-search-response').text('');
}