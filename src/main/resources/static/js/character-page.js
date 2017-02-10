function selectCharacterOption(index, characterId, characterName) {
	switch (index) {
	case 0:
		break;
	case 1:
		navigateToCharacterSheet(characterId);
		break;
	case 2:
		deleteCharacter(characterId, characterName);
		break;
	}
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