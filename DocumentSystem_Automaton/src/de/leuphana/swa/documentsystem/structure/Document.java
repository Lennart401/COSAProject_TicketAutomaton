package de.leuphana.swa.documentsystem.structure;

public class Document  {
	private static Integer lastId = 0;
	
	private Integer id;
	private String title;
	private String text;
	private DocumentFormat documentFormat;

	public Document(String title) {
		this.title = title;
		id = ++lastId;
	}

	public Integer getId() {
		return id;
	}
	
	public String getTitle() {
		return title;
	}

	public void setDocumentFormat(DocumentFormat documentFormat) {
		this.documentFormat = documentFormat;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}


}
