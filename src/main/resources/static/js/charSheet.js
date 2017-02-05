$(document).ready(function() {
	if ($('#totalHeader').length) {
		calculateAttributeMod('#strMod', $('#strTotal').text());
		calculateAttributeMod('#dexMod', $('#dexTotal').text());
		calculateAttributeMod('#conMod', $('#conTotal').text());
		calculateAttributeMod('#intMod', $('#intTotal').text());
		calculateAttributeMod('#wisMod', $('#wisTotal').text());
		calculateAttributeMod('#chaMod', $('#chaTotal').text());
	} else {
		calculateAttributeMod('#strMod', parseInt($('#strengthBase').attr('placeholder')) + parseInt($('#strengthEnhance').attr('placeholder')));
		calculateAttributeMod('#dexMod', parseInt($('#dexterityBase').attr('placeholder')) + parseInt($('#dexterityEnhance').attr('placeholder')));
		calculateAttributeMod('#conMod', parseInt($('#constitutionBase').attr('placeholder')) + parseInt($('#constitutionEnhance').attr('placeholder')));
		calculateAttributeMod('#intMod', parseInt($('#intelligenceBase').attr('placeholder')) + parseInt($('#intelligenceEnhance').attr('placeholder')));
		calculateAttributeMod('#wisMod', parseInt($('#wisdomBase').attr('placeholder')) + parseInt($('#wisdomEnhance').attr('placeholder')));
		calculateAttributeMod('#chaMod', parseInt($('#charismaBase').attr('placeholder')) + parseInt($('#charismaEnhance').attr('placeholder')));
	}
});

function calculateEditModeMods() {
	calculateAttributeMod('#strMod', getValue('#strengthBase', '#strengthEnhance'));
	calculateAttributeMod('#dexMod', getValue('#dexterityBase', '#dexterityEnhance'));
	calculateAttributeMod('#conMod', getValue('#constitutionBase', '#constitutionEnhance'));
	calculateAttributeMod('#intMod', getValue('#intelligenceBase', '#intelligenceEnhance'));
	calculateAttributeMod('#wisMod', getValue('#wisdomBase', '#wisdomEnhance'));
	calculateAttributeMod('#chaMod', getValue('#charismaBase', '#charismaEnhance'));
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
	
	return parseInt(baseVal) + parseInt(enhanceVal);
}

function calculateAttributeMod(el, val) {
	var mod = ((val - (val % 2)) - 10) / 2
	
	if (mod > 0) {
		mod = '+' + mod;
	}
	
	$(el).text(mod);
}