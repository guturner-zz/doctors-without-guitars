$(document).ready(function() {
	var hiddenImagePath = $('#imageField').val();
	if (hiddenImagePath != '') {
		$('#characterPortrait').attr('src', hiddenImagePath);
	}
});

function selectCharacterOption(index, characterId, characterName) {
	switch (index) {
	case 0:
		break;
	case 1:
		navigateToEditCharacter(characterId);
		break;
	case 2:
		navigateToCharacterSheet(characterId);
		break;
	case 3:
		deleteCharacter(characterId, characterName);
		break;
	}
}

function returnToCharacterSelect() {
	window.location.href = 'characters';
}

function navigateToEditCharacter(characterId) {
	window.location.href = 'editCharacter?id=' + characterId;
}

function navigateToCharacterSheet(characterId) {
	window.location.href = 'characterSheet?id=' + characterId;
}

function deleteCharacter(characterId, characterName) {
	var response = confirm("Are you sure you wish to delete " + characterName + "? This cannot be undone.");
	if (response == true) {
		postForm('#deleteCharacterForm-' + characterId);
	}
}