$(document).ready(function() {
	// If EditMode == False
	if ($('#totalHeader').length) {
		calculateAttributeMod('[name=strMod]', $('#strTotal').text());
		calculateAttributeMod('[name=dexMod]', $('#dexTotal').text());
		calculateAttributeMod('[name=conMod]', $('#conTotal').text());
		calculateAttributeMod('[name=intMod]', $('#intTotal').text());
		calculateAttributeMod('[name=wisMod]', $('#wisTotal').text());
		calculateAttributeMod('[name=chaMod]', $('#chaTotal').text());
		
		toggleRanged();
		
		calculateMaxHp('[name=maxHp]', $('#hitDie').text());

	// If EditMode == True
	} else {
		calculateAttributeMod('[name=strMod]', parseInt($('#strengthBase').attr('placeholder')) + parseInt($('#strengthEnhance').attr('placeholder')));
		calculateAttributeMod('[name=dexMod]', parseInt($('#dexterityBase').attr('placeholder')) + parseInt($('#dexterityEnhance').attr('placeholder')));
		calculateAttributeMod('[name=conMod]', parseInt($('#constitutionBase').attr('placeholder')) + parseInt($('#constitutionEnhance').attr('placeholder')));
		calculateAttributeMod('[name=intMod]', parseInt($('#intelligenceBase').attr('placeholder')) + parseInt($('#intelligenceEnhance').attr('placeholder')));
		calculateAttributeMod('[name=wisMod]', parseInt($('#wisdomBase').attr('placeholder')) + parseInt($('#wisdomEnhance').attr('placeholder')));
		calculateAttributeMod('[name=chaMod]', parseInt($('#charismaBase').attr('placeholder')) + parseInt($('#charismaEnhance').attr('placeholder')));
		
		calculateMaxHp('[name=maxHp]', getInputValue('#hitDie'));
	}
	
	// Hide panes:
	$('#combatPane').hide();
	
	// Add calculate value events:
	$('input:not(:checkbox)').each(
		function() {
			$(this).blur(function() {
				recalculateVals();
			});
		}
	);
});

function recalculateVals() {
	calculateAttributeMod('[name=strMod]', getValue('#strengthBase', '#strengthEnhance'));
	calculateAttributeMod('[name=dexMod]', getValue('#dexterityBase', '#dexterityEnhance'));
	calculateAttributeMod('[name=conMod]', getValue('#constitutionBase', '#constitutionEnhance'));
	calculateAttributeMod('[name=intMod]', getValue('#intelligenceBase', '#intelligenceEnhance'));
	calculateAttributeMod('[name=wisMod]', getValue('#wisdomBase', '#wisdomEnhance'));
	calculateAttributeMod('[name=chaMod]', getValue('#charismaBase', '#charismaEnhance'));
	
	calculateMaxHp('[name=maxHp]', getInputValue('#hitDie'));
	validateHp('[name=currentHp]', getInputValue('[name=currentHp]'), getInputValue('#hitDie'));
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

function getMaxHp(hitDieVal) {
	var numOfDie = parseInt(hitDieVal.substring(0,1));
	var hpMod = parseInt(hitDieVal.substring(2,4));
	
	return (numOfDie * hpMod);
}

function calculateMaxHp(el, hitDieVal) {
	var maxHpVal = getMaxHp(hitDieVal);
	
	$(el).each(function() {
		$(this).text(maxHpVal);
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

function validateHp(el, currentHp, hitDieVal) {
	var maxHp = getMaxHp(hitDieVal);
	var calculatedHp = currentHp;
	
	if (currentHp < 0) {
		calculatedHp = 0;
	}
	
	if (currentHp > maxHp) {
		calculatedHp = maxHp;
	}
	
	if (isNaN(parseInt(currentHp))) {
		calculatedHp = 0;
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