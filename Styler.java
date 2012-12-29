import java.util.ArrayList;
import java.util.HashMap;

import org.jsoup.nodes.Element;

/**
 * Styler class: facilitates books scraping and styles sections for GUI display
 * and export to text. Uses the SINGLETON design pattern.
 * 2012-05-09
 * @author Jane Kim (jk3316) & Ben Kogan (bmk2130)
 */
public class Styler {

	private static Styler instance; // instance of this class
	private InfoScraper scraper;

	/**
	 * Constructor.
	 */
	private Styler() {
		scraper = InfoScraper.getInstance();
	}

	/**
	 * Creates or retrieves instance of Styler. Uses singleton design pattern
	 * to ensure only one instance instance exists.
	 * @return instance
	 */
	public static Styler getInstance() {
		// create a new instance of Styler if one doesn't exist yet
		if (instance == null) {
			instance = new Styler();
		}
		return instance;
	}

	/**
	 * Tells scraper to perform initial book scrape.
	 */
	public void scrapeBooks() {
		scraper.initialScrape();
	}

	/**
	 * Getter for books.
	 * @return books.
	 */
	public HashMap<String, Book> getBooks() {
		return scraper.getBooks();
	}

	/**
	 * Getter for book titles.
	 * @return bookTitles
	 */
	public ArrayList<String> getTitles() {
		return scraper.getTitles();
	}

	/**
	 * Getter for section titles for a given book.
	 * @param bookTitle to grab from
	 * @return sectionTitles
	 */
	public ArrayList<String> getSectTitles(String bookTitle) {
		return scraper.getBookSectionTitles(bookTitle);
	}

	/**
	 * Parses and formats book contents sections for given book. Updates books
	 * data structure stored in scraper, and so GUI class must then use a
	 * getter to retrieve the updated books list.
	 * @param bookTitle from which to grab
	 */
	public void parseBookContents(String bookTitle) {
		scraper.getBookWithSections(bookTitle);
		formatSections(bookTitle);
	}

	/**
	 * Gets content for a given section of a given book formatted for export.
	 * @param bookTitle
	 * @param sectTitle
	 * @return content for export
	 */
	public String getSectExport(String bookTitle, String sectTitle) {
		return getBooks().get(bookTitle).getSections().get(sectTitle)
				.getContentForExport();
	}

	/**
	 * Gets content for a given section of a given book formatted for display.
	 * @param bookTitle
	 * @param sectTitle
	 * @return content for display
	 */
	public String getSectDisplay(String bookTitle, String sectTitle) {
		return getBooks().get(bookTitle).getSections().get(sectTitle)
				.getContentForDisplay();
	}

	/**
	 * Parses and formats sections from a given book for display and export.
	 * @param bookTitle
	 */
	private void formatSections(String bookTitle) {
		// gets book sections and section titles
		HashMap<String, Section> sections = scraper.getBookSections(bookTitle);
		ArrayList<String> titles = scraper.getBookSectionTitles(bookTitle);

		// retrieves and formats each section
		for (String t : titles) {
			Element target = sections.get(t).getContent()
					.getElementsByClass("studyGuideText").first();

			scraper.getBooks().get(bookTitle)
			.setSectContentForDisp(t, target.html());
			scraper.getBooks().get(bookTitle)
			.setSectContentForExpt(t, target.text());
		}
	}

}