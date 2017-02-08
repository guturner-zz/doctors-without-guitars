function disableFormElements() {
	$('input:not(:checkbox)').each(
		function() {
			$(this).attr('disabled', 'disabled');
		}
	);

	$('select').each(
		function() {
			$(this).attr('disabled', 'disabled');
		}
	);
	
	// Re-enable DM Search Bar:
	$('#cd-search-val').removeAttr('disabled');
}

function postForm(formId) {
	$(formId).submit();
}