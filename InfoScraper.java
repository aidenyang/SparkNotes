import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * InfoScraper class: parses and stores information for SparkNotes site. Uses
 * the SINGLETON design pattern.
 * 2012-05-09
 * @author Ben Kogan (bmk2130)
 */
public class InfoScraper {

	private static InfoScraper instance; // instance of this class
	private HashMap<String, Book> books; // books
	private ArrayList<String> titles; // book titles

	/**
	 * Constructor.
	 */
	private InfoScraper() {
		books = new HashMap<String, Book>();
		titles = new ArrayList<String>();
	}

	/**
	 * Creates or retrieves instance of InfoScraper. Uses singleton design
	 * pattern to ensure only one instance instance exists.
	 * @return instance
	 */
	public static InfoScraper getInstance() {
		// create a new instance of InfoScraper if one doesn't exist yet
		if (instance == null) {
			instance = new InfoScraper();
		}
		return instance;
	}

	/**
	 * Initial scrape of book titles, URLs, and authors.
	 */
	public void initialScrape() {
		buildBooks();
	}

	/**
	 * Getter for books.
	 * @return books
	 */
	public HashMap<String, Book> getBooks() {
		return books;
	}

	/**
	 * Getter for titles.
	 * @return titles
	 */
	public ArrayList<String> getTitles() {
		return titles;
	}

	/**
	 * Getter for book sections for specified book.
	 * @param bookTitle from which to grab
	 * @return sections from chosen book
	 */
	public HashMap<String, Section> getBookSections(String bookTitle) {
		return books.get(bookTitle).getSections();
	}

	/**
	 * Getter for book section titles.
	 * @param bookTitle from which to grab
	 * @return section titles from chosen book
	 */
	public ArrayList<String> getBookSectionTitles(String bookTitle) {
		return books.get(bookTitle).getSectionTitles();
	}

	/**
	 * Connects to chosen book's page and parses sections.
	 * @param title of book to parse
	 * @return newBook with updates
	 */
	public Book getBookWithSections(String title) {
		Document doc = null;
		Book newBook = books.get(title);

		try {
			doc = Jsoup.connect(newBook.getURL()).get();

			// gets all section elements
			Elements sections = doc.getElementsByAttributeValueStarting(
					"class", "entry ");
			String name = null;
			String url = null;

			// parses each element for a name and url
			for (Element s : sections) {
				Element target = s.getElementsByAttributeValueStarting("href",
						"http://www.sparknotes.com/").first();

				if (target != null) {
					name = target.text();
					url = target.attr("href");

					if (!name.equals("Important Quotations Explained")
						  && !name.equals("Key Facts")
						  && !name.equals("Study Questions &amp; Essay Topics")
						  && !name.equals("Quiz")
					  	  && !name.equals("Suggestions for Further Reading")
						  && !name.equals("How to Cite This SparkNote"))
						newBook.addSection(name, url); // adds section
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return newBook;
	}

	/**
	 * Builds initial list of books. Contains title, author, and url for each
	 * book.
	 */
	private void buildBooks() {
		Document doc = null;

		for (int i = 97; i < 123; i++) { // iterates through each alphabet page
			try {

				doc = Jsoup.connect(
						"http://www.sparknotes.com/lit/index_" + (char) i
								+ ".html").get();

				// retrieves elements for all books
				Elements entries = doc.getElementsByAttributeValueStarting(
						"class", "entry ");
				String title = null;
				String author = null;
				String url = null;

				// parses each element for a title, author, and url
				for (Element e : entries) {
					Element target = e.getElementsByAttributeValueStarting(
							"href", "http://www.sparknotes.com/").first();

					title = target.text();
					author = e.getElementsByClass("right").text();
					url = target.attr("href");

					// adds book
					books.put(title, new Book(title, author, url));
					titles.add(title);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}