package de.leuphana.cosa.pricingsystem.structure;

/**
 * @author Lennart_Admin
 */
public enum PriceGroup {
	NORMAL_TARIF("Normal-Tarif"),
	GUENSTIGERREISEN_TARIF("GünstigerReisen-Tarif"),
	SCHNAEPPCHEN_TARIF("Schnäppchen-Tarif");

	private String displayName;

	PriceGroup(String displayName) {
		this.displayName = displayName;
	}

	public String getDisplayName() {
		return displayName;
	}
}
