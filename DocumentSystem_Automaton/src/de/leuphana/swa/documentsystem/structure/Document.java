package de.leuphana.swa.documentsystem.structure;

public class Document  {
	private static Integer lastId = 0;
	
	private Integer id;
	// TODO delete because of name parameter
	private String titel;
	private String text;
	private DocumentFormat documentFormat;

	public Document(String titel) {
		this.titel = titel;
		id = ++lastId;
	}

	public Integer getId() {
		return id;
	}
	
	public String getTitel() {
		return titel;
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
