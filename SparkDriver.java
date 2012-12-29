import java.util.ArrayList;
import java.util.HashMap;

/**
 * Test driver used for testing scraping.
 * 2012-05-09
 * 
 * @author Ben Kogan (bmk2130)
 *
 */
public class SparkDriver {

	/**
	 * Main method.
	 * @param args
	 */
//	public static void main(String[] args) {
//		
//		InfoScraper scraper = InfoScraper.getInstance();
//		scraper.initialScrape();
//		
//		// gets titles, urls, and authors for each book 
//		HashMap<String, Book> books = new HashMap<String, Book>();
//		books = scraper.getBooks();
//		ArrayList<String> titles = scraper.getTitles();
//		
//		// prints books in alphabetical order (inc their authors)
//		// uses titles list to call from HashMap in alpha order
//		for (String title : titles) {
//			Book current = books.get(title);
//			String toPrint = "Book: " + current.getTitle() + "\n" +
//							 "Author: " + current.getAuthor() + "\n\n";
//					
//			System.out.println(toPrint);
//		}			
//
//	}

	public static void main(String[] args) {
		
		Styler robot = Styler.getInstance();
		robot.scrapeBooks();
		
		// gets titles, urls, and authors for each book 
		HashMap<String, Book> books = new HashMap<String, Book>();
		books = robot.getBooks();
		ArrayList<String> titles = robot.getTitles();
		
		// prints books in alphabetical order (inc their authors)
		// uses titles list to call from HashMap in alpha order
		for (String title : titles) {
			Book current = books.get(title);
			String toPrint = "Book: " + current.getTitle() + "\n" +
							 "Author: " + current.getAuthor() + "\n\n";
					
			System.out.println(toPrint);
			robot.parseBookContents(title);
			
			for (String sectTitle : robot.getBooks().get(title).getSectionTitles()) {
				System.out.println("Content for Export: " + robot.getSectExport(title, sectTitle) + "\n\n");
			}
			
			break;
		}			

	}
	
}
