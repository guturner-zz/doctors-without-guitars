$(document).ready(function() {
	if ($('#totalHeader').length) {
		calculateAttributeMod('[name=strMod]', $('#strTotal').text());
		calculateAttributeMod('[name=dexMod]', $('#dexTotal').text());
		calculateAttributeMod('[name=conMod]', $('#conTotal').text());
		calculateAttributeMod('[name=intMod]', $('#intTotal').text());
		calculateAttributeMod('[name=wisMod]', $('#wisTotal').text());
		calculateAttributeMod('[name=chaMod]', $('#chaTotal').text());
		
		toggleRanged();
	} else {
		calculateAttributeMod('[name=strMod]', parseInt($('#strengthBase').attr('placeholder')) + parseInt($('#strengthEnhance').attr('placeholder')));
		calculateAttributeMod('[name=dexMod]', parseInt($('#dexterityBase').attr('placeholder')) + parseInt($('#dexterityEnhance').attr('placeholder')));
		calculateAttributeMod('[name=conMod]', parseInt($('#constitutionBase').attr('placeholder')) + parseInt($('#constitutionEnhance').attr('placeholder')));
		calculateAttributeMod('[name=intMod]', parseInt($('#intelligenceBase').attr('placeholder')) + parseInt($('#intelligenceEnhance').attr('placeholder')));
		calculateAttributeMod('[name=wisMod]', parseInt($('#wisdomBase').attr('placeholder')) + parseInt($('#wisdomEnhance').attr('placeholder')));
		calculateAttributeMod('[name=chaMod]', parseInt($('#charismaBase').attr('placeholder')) + parseInt($('#charismaEnhance').attr('placeholder')));
	}
	
	// Hide panes:
	$('#combatPane').hide();
});

function calculateEditModeMods() {
	calculateAttributeMod('[name=strMod]', getValue('#strengthBase', '#strengthEnhance'));
	calculateAttributeMod('[name=dexMod]', getValue('#dexterityBase', '#dexterityEnhance'));
	calculateAttributeMod('[name=conMod]', getValue('#constitutionBase', '#constitutionEnhance'));
	calculateAttributeMod('[name=intMod]', getValue('#intelligenceBase', '#intelligenceEnhance'));
	calculateAttributeMod('[name=wisMod]', getValue('#wisdomBase', '#wisdomEnhance'));
	calculateAttributeMod('[name=chaMod]', getValue('#charismaBase', '#charismaEnhance'));
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
	
	if (mod > 0) {
		mod = '+' + mod;
	}
	
	$(el).each(function() {
		$(this).text(mod);
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
		$("#rangeCombat").html("1d20 <span name='dexMod'></span>");
		calculateAttributeMod('[name=dexMod]', $('#dexTotal').text());
	} else {
		$("#rangeCombat").html("1d20 <span name='strMod'></span>");
		calculateAttributeMod('[name=strMod]', $('#strTotal').text());
	}
}