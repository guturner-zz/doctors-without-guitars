function disableFormElements() {
	$('input').each(
		function() {
			$(this).attr('disabled', 'disabled');
		}
	);

	$('select').each(
		function() {
			$(this).attr('disabled', 'disabled');
		}
	);
}

function postForm(formId) {
	$(formId).submit();
}