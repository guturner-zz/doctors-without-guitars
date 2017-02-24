package org.guy.rpg.dwg.pdf;

import java.io.ByteArrayOutputStream;
import java.net.MalformedURLException;

import org.assertj.core.util.diff.Chunk;
import org.guy.rpg.dwg.models.db.Character;
import org.guy.rpg.dwg.models.db.CharacterSheet;
import org.springframework.stereotype.Component;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.VerticalAlignment;

/**
 * Helper for PDF generation. Uses the iText API.
 * 
 * @author Guy
 */
@Component
public class PDFManager {

	public ByteArrayOutputStream getPDFAsBytes(Character character, CharacterSheet characterSheet) throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		PdfWriter writer = new PdfWriter(baos);
		PdfDocument pdf = new PdfDocument(writer);
		Document document = new Document(pdf);
		
		addLogoLine(document, character);
		addEmptyLine(document);
		addAttributesTable(document, characterSheet);
		addEmptyLine(document);
		addPropertiesTable(document, character, characterSheet);
		
		document.close();

		return baos;
	}

	/**
	 * Adds a two column table to the PDF.
	 * Left column is the logo, right column is the character's name and class.
	 */
	private void addLogoLine(Document doc, Character character) throws MalformedURLException {
		Image img = new Image(ImageDataFactory.create("src/main/resources/static/img/logo.png"));

		String line = character.getName() + " the " + character.getCharClass().getName();
		Paragraph p = new Paragraph(line);

		Table table = new Table(2);
		table.setBorder(Border.NO_BORDER);

		Cell cellLeft = new Cell();
		cellLeft.setBorder(Border.NO_BORDER);

		Cell cellRight = new Cell();
		cellRight.setFontSize(25);
		cellRight.setVerticalAlignment(VerticalAlignment.MIDDLE);
		cellRight.setBorder(Border.NO_BORDER);

		cellLeft.add(img.scale(0.5f, 0.5f));
		cellRight.add(p);

		table.addCell(cellLeft);
		table.addCell(cellRight);

		doc.add(table);
	}

	/**
	 * Adds a four column table to the PDF.
	 * Attribute name, base value, enhance value, mod.
	 */
	private void addAttributesTable(Document doc, CharacterSheet characterSheet) throws MalformedURLException {
		Table table = new Table(4);
		table.setBorder(Border.NO_BORDER);

		// Headers:
		addCellToTable(table, "Attribute Name");
		addCellToTable(table, "Base Value");
		addCellToTable(table, "Enhance Value");
		addCellToTable(table, "Mod");

		// Strength
		addCellToTable(table, "STR");

		Integer strBase = characterSheet.getStrengthBase();
		addCellToTable(table, strBase.toString());

		Integer strEnhance = characterSheet.getStrengthEnhance();
		addCellToTable(table, strEnhance.toString());

		addCellToTable(table, calculateAttributeMod(strBase, strEnhance));

		// Dexterity
		addCellToTable(table, "DEX");

		Integer dexBase = characterSheet.getDexterityBase();
		addCellToTable(table, dexBase.toString());

		Integer dexEnhance = characterSheet.getDexterityEnhance();
		addCellToTable(table, dexEnhance.toString());
		
		addCellToTable(table, calculateAttributeMod(dexBase, dexEnhance));

		// Constitution
		addCellToTable(table, "CON");

		Integer conBase = characterSheet.getConstitutionBase();
		addCellToTable(table, conBase.toString());

		Integer conEnhance = characterSheet.getConstitutionEnhance();
		addCellToTable(table, conEnhance.toString());
		
		addCellToTable(table, calculateAttributeMod(conBase, conEnhance));

		// Intelligence
		addCellToTable(table, "INT");

		Integer intBase = characterSheet.getIntelligenceBase();
		addCellToTable(table, intBase.toString());

		Integer intEnhance = characterSheet.getIntelligenceEnhance();
		addCellToTable(table, intEnhance.toString());
		
		addCellToTable(table, calculateAttributeMod(intBase, intEnhance));

		// Wisdom
		addCellToTable(table, "WIS");

		Integer wisBase = characterSheet.getWisdomBase();
		addCellToTable(table, wisBase.toString());

		Integer wisEnhance = characterSheet.getWisdomEnhance();
		addCellToTable(table, wisEnhance.toString());

		addCellToTable(table, calculateAttributeMod(wisBase, wisEnhance));

		// Charisma
		addCellToTable(table, "CHA");

		Integer chaBase = characterSheet.getCharismaBase();
		addCellToTable(table, chaBase.toString());

		Integer chaEnhance = characterSheet.getCharismaEnhance();
		addCellToTable(table, chaEnhance.toString());

		addCellToTable(table, calculateAttributeMod(chaBase, chaEnhance));

		doc.add(table);
	}
	
	/**
	 * Adds a two column table to the PDF.
	 * Property name and property value.
	 */
	private void addPropertiesTable(Document doc, Character character, CharacterSheet characterSheet) throws MalformedURLException {
		Table table = new Table(2);
		table.setBorder(Border.NO_BORDER);

		// Headers:
		addCellToTable(table, "Property Name");
		addCellToTable(table, "Property Value");

		// Hit Die
		addCellToTable(table, "Hit Die");
		addCellToTable(table, characterSheet.getHitDie());

		// Size
		addCellToTable(table, "Size Mod");
		addCellToTable(table, character.getSize().getName() + " (" + character.getSize().getValue() + ")");

		doc.add(table);
	}

	/**
	 * Adds a cell with the supplied text to the table.
	 */
	private void addCellToTable(Table table, String cellValue) {
		Cell cell = new Cell();
		cell.add(new Paragraph(cellValue));
		table.addCell(cell);
	}

	/**
	 * Adds three empty lines to the PDF.
	 */
	private void addEmptyLine(Document doc) {
		for (int i = 0; i < 3; i++) {
			doc.add(new Paragraph(" "));
		}
	}

	/**
	 * Given a base value and enhance value, returns the corresponding Attribute Mod.
	 * ( ( sum - ( sum % 2 ) ) - 10 ) / 2 )
	 */
	private String calculateAttributeMod(Integer base, Integer enhance) {
		Integer val = base + enhance;
		Integer mod = new Integer(((val - (val % 2)) - 10) / 2);

		String modVal = mod.toString();
		if (mod > -1) {
			modVal = "+" + modVal;
		}
		
		return modVal;
		
	}

}
