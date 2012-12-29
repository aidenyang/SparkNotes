import org.jsoup.nodes.Element;

/**
 * Section class: stores information for a single section summary of a book.
 * 2012-05-09
 * @author Ben Kogan (bmk2130)
 */
public class Section {

	private String name; // section name
	private String url; // section url
	private Element content; // section content
	private String contentForDisplay; // content formatted for GUI
	private String contentForExport; // content formatted for text export

	/**
	 * Constructor.
	 * @param name for section
	 * @param url for section
	 */
	public Section(String name, String url) {
		this.name = name;
		this.url = url;

		content = null;
		contentForDisplay = null;
		contentForExport = null;
	}

	/**
	 * Getter for name.
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Getter for URL.
	 * @return url
	 */
	public String getURL() {
		return url;
	}

	/**
	 * Setter for content.
	 * @param content to set to
	 */
	public void setContent(Element content) {
		this.content = content;
	}

	/**
	 * Getter for content.
	 * @return content
	 */
	public Element getContent() {
		return content;
	}

	/**
	 * Getter for display content.
	 * @return contentForDisplay
	 */
	public String getContentForDisplay() {
		return contentForDisplay;
	}

	/**
	 * Setter for display content.
	 * @param c content to set to
	 */
	public void setContentForDisplay(String c) {
		contentForDisplay = c;
	}

	/**
	 * Getter for export content.
	 * @return contentForExport
	 */
	public String getContentForExport() {
		return contentForExport;
	}

	/**
	 * Setter for export content.
	 * @param c content to set to
	 */
	public void setContentForExport(String c) {
		contentForExport = c;
	}

}
