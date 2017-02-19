$(document).ready(function() {
	// If EditMode == False
	if ($('#totalHeader').length) {
		prepareViewCharacterSheetFields();
		
	// If EditMode == True
	} else {
		prepareEditCharacterSheetFields();
	}
	
	calculateBaseAttackBonus('[name=bab]', getInputText('#baseAttackBonus'));
	
	showPanel("#attributesPanel");
	
	// Hide panes:
	$('#combatPane').hide();
	$('#tipsPane').hide();
	
	// Add calculate value events:
	$('input:not(:checkbox)').each(
		function() {
			$(this).blur(function() {
				recalculateVals();
			});
		}
	);
	
	// Used on 'First Steps' screen to auto set Hit Die:
	$('#hitDieHint').click(function() {
		var hitDieVal = $('#hitDieHint').text();
		$('#hitDie').val(hitDieVal);
	});
});

function showPanel(panelId) {
	$("div[name='panel']").hide();
	$(panelId).show();
}

function prepareViewCharacterSheetFields() {
	// Formats attribute mods:
	calculateAttributeMod('[name=strMod]', $('#strTotal').text());
	calculateAttributeMod('[name=dexMod]', $('#dexTotal').text());
	calculateAttributeMod('[name=conMod]', $('#conTotal').text());
	calculateAttributeMod('[name=intMod]', $('#intTotal').text());
	calculateAttributeMod('[name=wisMod]', $('#wisTotal').text());
	calculateAttributeMod('[name=chaMod]', $('#chaTotal').text());
	
	// Sets the 'Melee / Ranged' combat tip values:
	toggleRanged();
	
	// Sets the HP bars:
	setProgressBars();
}

function prepareEditCharacterSheetFields() {
	// Formats attribute mods based on base and enhance vlaues:
	calculateAttributeMod('[name=strMod]', parseInt($('#strengthBase').attr('placeholder')) + parseInt($('#strengthEnhance').attr('placeholder')));
	calculateAttributeMod('[name=dexMod]', parseInt($('#dexterityBase').attr('placeholder')) + parseInt($('#dexterityEnhance').attr('placeholder')));
	calculateAttributeMod('[name=conMod]', parseInt($('#constitutionBase').attr('placeholder')) + parseInt($('#constitutionEnhance').attr('placeholder')));
	calculateAttributeMod('[name=intMod]', parseInt($('#intelligenceBase').attr('placeholder')) + parseInt($('#intelligenceEnhance').attr('placeholder')));
	calculateAttributeMod('[name=wisMod]', parseInt($('#wisdomBase').attr('placeholder')) + parseInt($('#wisdomEnhance').attr('placeholder')));
	calculateAttributeMod('[name=chaMod]', parseInt($('#charismaBase').attr('placeholder')) + parseInt($('#charismaEnhance').attr('placeholder')));
	
	// Add submit form event to save button:
	$('#saveBtn').click(function() {
		postForm('#saveForm');
	});
	
	// Guarantees weapon fields aren't inconsistent:
	$('[name^=weapon]').blur(function() {
		var weaponNameVal = $('#weaponName').val();
		
		if (weaponNameVal == null || weaponNameVal == '') {
			$('#weaponDamage').val('');
			$('#weaponCrit').val('');
		}
	});
}

function cancelCharacterSheet(characterId) {
	window.location.href = 'characterSheet?id=' + characterId;
}

function recalculateVals() {
	calculateAttributeMod('[name=strMod]', getValue('#strengthBase', '#strengthEnhance'));
	calculateAttributeMod('[name=dexMod]', getValue('#dexterityBase', '#dexterityEnhance'));
	calculateAttributeMod('[name=conMod]', getValue('#constitutionBase', '#constitutionEnhance'));
	calculateAttributeMod('[name=intMod]', getValue('#intelligenceBase', '#intelligenceEnhance'));
	calculateAttributeMod('[name=wisMod]', getValue('#wisdomBase', '#wisdomEnhance'));
	calculateAttributeMod('[name=chaMod]', getValue('#charismaBase', '#charismaEnhance'));
	
	validateHp('[name=currentHp]', getInputValue('#currentHp'), getInputValue('#maxHp'));
}

function getInputText(valEl) {
	var val = $(valEl).text();
	if (val == "NaN" || val == "") {
		val = $(valEl).attr('placeholder');
	}
	
	return val;
}

function getInputValue(valEl) {
	var val = $(valEl).val();
	if (val == "NaN" || val == "") {
		val = $(valEl).attr('placeholder');
	}
	
	return val;
}

function getValue(baseEl, enhanceEl) {
	var baseVal = $(baseEl).val();
	if (baseVal == "NaN" || baseVal == "") {
		baseVal = $(baseEl).attr('placeholder');
	}
	
	var enhanceVal = $(enhanceEl).val();
	if (enhanceVal == "NaN" || enhanceVal == "") {
		enhanceVal = $(enhanceEl).attr('placeholder');
	}
	
	// Validation:
	if ( isNaN(parseInt(baseVal) + parseInt(enhanceVal)) ) {
		$(baseEl).val("");
		$(enhanceEl).val("");
		return getValue(baseEl, enhanceEl);
	}
	
	return parseInt(baseVal) + parseInt(enhanceVal);
}

function calculateAttributeMod(el, val) {
	var mod = ((val - (val % 2)) - 10) / 2
	
	if (mod > -1) {
		mod = '+' + mod;
	}
	
	$(el).each(function() {
		$(this).text(mod);
	});
}

function calculateSizeMod(el, sizeModVal) {
	var sizeModCalculatedVal = parseInt(sizeModVal);
	
	if (sizeModCalculatedVal > -1) {
		sizeModCalculatedVal = '+' + sizeModCalculatedVal;
	}
	
	$(el).each(function() {
		$(this).text(sizeModCalculatedVal);
	});
}

function calculateBaseAttackBonus(el, bab) {
	var babVal = bab;
	
	if (babVal > -1) {
		babVal = '+' + babVal;
	}
	
	$(el).each(function() {
		$(this).text(babVal);
	});
}

function validateHp(el, currentHp, maxHp) {
	var currentHpVal = parseInt(currentHp);
	var maxHpVal = parseInt(maxHp);
	var calculatedHp = currentHpVal;
	
	if (isNaN(currentHpVal) || isNaN(maxHpVal)) {
		calculatedHp = 0;
	}
	
	if (currentHpVal < 0) {
		calculatedHp = 0;
	}
	
	if (currentHpVal > maxHpVal) {
		calculatedHp = maxHpVal;
	}
	
	$(el).each(function() {
		$(this).val(calculatedHp);
	});
}

function showPane(el) {
	if ($(el).is(":visible")) {
		$(el).hide();
	} else {
		$(el).show();
	}
}

function toggleRanged() {
	if ($("#rangeChk").is(":checked")) {
		$("#rangeCombat").html("1d20 <span name='dexMod'></span> (DEX Mod) <span name='sizeMod'></span> (Size Mod)");
		calculateAttributeMod('[name=dexMod]', $('#dexTotal').text());
	} else {
		$("#rangeCombat").html("1d20 <span name='strMod'></span> (STR Mod) <span name='sizeMod'></span> (Size Mod)");
		calculateAttributeMod('[name=strMod]', $('#strTotal').text());
	}
	
	// Need to recalculate mods used in dynamic fields:
	calculateSizeMod('[name=sizeMod]', getInputText('#sizeMod'));
}

function setProgressBars() {
	var currentHp = parseInt($('#hpBarCurrent').text());
	var maxHp = parseInt($('#hpBarMax').text());
	
	// Validation:
	if (maxHp == 0) {
		return "error";
	}
	
	var percentage = Math.round((currentHp / maxHp) * 100);
	
	var cssClass = "progress-bar-info";
	
	if (percentage > 75) {
		cssClass = "progress-bar-success";
	}
	
	if (percentage < 25) {
		cssClass = "progress-bar-danger";
		var element = $('#hpText').detach();
		$('#progressBarParent').append(element);
	}
	
	$('#hpProgressBar').addClass('progress-bar ' + cssClass);
	$('#hpProgressBar').css("width", percentage + "%");
}