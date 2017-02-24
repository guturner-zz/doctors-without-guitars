package org.guy.rpg.dwg.controllers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.guy.rpg.dwg.db.DatabaseManager;
import org.guy.rpg.dwg.db.repositories.CharacterSheetRepository;
import org.guy.rpg.dwg.models.db.Character;
import org.guy.rpg.dwg.models.db.CharacterSheet;
import org.guy.rpg.dwg.models.db.User;
import org.guy.rpg.dwg.pdf.PDFManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller for viewing Character Sheets as PDFs.
 * 
 * @author Guy
 */
@Controller
public class PDFController extends BaseController {

	private static final Logger LOGGER = LoggerFactory.getLogger(PDFController.class);

	@Autowired
	DatabaseManager dbManager;

	@Autowired
	PDFManager pdfManager;

	@Autowired
	CharacterSheetRepository characterSheetRepository;

	@GetMapping("/pdf")
	public ModelAndView viewAsPDF(@RequestParam("id") Long characterSheetId, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		// Validate that this is the current user's Character Sheet:
		User user = dbManager.getCurrentUser(request);
		CharacterSheet characterSheetFromId = characterSheetRepository.getCharacterSheetById(characterSheetId);

		ModelAndView modelAndView = new ModelAndView();
		Character character = getCharacterFromCharacterSheet(user, characterSheetFromId);
		if (character == null) {
			modelAndView.setViewName("redirect:/");
			return modelAndView;
		}

		try {
			ByteArrayOutputStream pdf = pdfManager.getPDFAsBytes(character, characterSheetFromId);
			String date = new SimpleDateFormat("MM-dd-yyyy").format(new Date());
			String pdfName = character.getName() + "-" + date + "-character-sheet.pdf";
			writePDF(response, pdf, pdfName);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}

		return modelAndView;
	}

	private void writePDF(HttpServletResponse response, ByteArrayOutputStream data, String name) throws IOException {
		response.setContentType("application/pdf");
		response.setHeader("Content-disposition", "attachment; filename=" + name);
		response.setContentLength(data.size());

		data.writeTo(response.getOutputStream());
		response.getOutputStream().flush();
	}

	private Character getCharacterFromCharacterSheet(User user, CharacterSheet characterSheet) {
		Character character = null;

		List<Character> characters = user.getCharacters();
		for (Character c : characters) {
			if (c.getCharSheet().equals(characterSheet)) {
				character = c;
				break;
			}
		}

		return character;
	}
}
