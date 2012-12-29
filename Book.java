import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * Book class: stores information for a book.
 * 2012-05-09
 * @author Ben Kogan (bmk2130)
 */
public class Book {

	private String title; // book title
	private String author; // book author
	private String url; // book URL
	private HashMap<String, Section> sections; // book summary sections
	private ArrayList<String> sectionTitles; // book summary section titles

	/**
	 * Constructor.
	 * @param title for book
	 * @param author for book
	 * @param url for book
	 */
	public Book(String title, String author, String url) {
		this.title = title;
		this.author = author;
		this.url = url;

		sections = new HashMap<String, Section>();
		sectionTitles = new ArrayList<String>();
	}

	/**
	 * Add a section's contents to sections data structure.
	 * @param name of section
	 * @param url of section
	 */
	public void addSection(String name, String url) {
		Document doc = null;
		Section newSection = new Section(name, url);

		try {
			doc = Jsoup.connect(newSection.getURL()).get();

			// get "studyGuideText" div containing content
			Element currentSect = doc.getElementsByClass("studyGuideText")
					.first();
			newSection.setContent(currentSect);
			//!!!: DO something in case this isn't there?

			sections.put(name, newSection);
			sectionTitles.add(name);

			//!!!: ADD a catch block to deal with page not found 
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Getter for URL.
	 * @return url
	 */
	public String getURL() {
		return url;
	}

	/**
	 * Getter for title.
	 * @return title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Getter for author.
	 * @return author
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * Getter for sections.
	 * @return sections
	 */
	public HashMap<String, Section> getSections() {
		return sections;
	}

	/**
	 * Getter for section titles.
	 * @return sectionTitles
	 */
	public ArrayList<String> getSectionTitles() {
		return sectionTitles;
	}

	/**
	 * Setter for display content.
	 * @param section
	 * @param c content to set to
	 */
	public void setSectContentForDisp(String section, String c) {
		sections.get(section).setContentForDisplay(c);
	}

	/**
	 * Setter for export content.
	 * @param section
	 * @param c content to set to
	 */
	public void setSectContentForExpt(String section, String c) {
		sections.get(section).setContentForExport(c);
	}
}
