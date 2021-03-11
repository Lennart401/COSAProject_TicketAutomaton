package de.leuphana.cosa.ticketautomaton.behaviour;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.function.Function;

/**
 * @author Lennart_Admin
 */
public class CLI {
	// Colors from Stackoverflow users WhiteFang34 and SergeyB
	// see https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";
	public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
	public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
	public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
	public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
	public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
	public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
	public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
	public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

	private static CLI instance = new CLI();

	private Scanner scanner;
	private boolean highlightPrinting;
	private String highlightingColor;

	private CLI() {
		this.scanner = new Scanner(System.in);
		this.highlightPrinting = true;
		this.highlightingColor = ANSI_RED;
	}

	public void info(String s) {
		println(s);
	}

	public void prompt(String s) {
		println("");
		print(s);
	}

	public void error(String s) {
		println("Error: " + s);
	}

	/**
	 * Displays all options to the user and makes them select one.
	 * @param options The options the user should be shown
	 * @return the selected option
	 */
	public String displayAndSelect(Set<String> options, String prompt) {
		List<String> sps = List.copyOf(options);

		for (int i = 0; i < sps.size(); i++) {
			println((i + 1) + " " + sps.get(i));
		}

		prompt(prompt);
		int startpointIndex = scanner.nextInt();
		return sps.get(startpointIndex - 1);
	}

	public String displayAndSelect(Map<String, Object> keyValuePairs, String prompt, Function<Object, String> printFormatter) {
		List<String> keys = List.copyOf(keyValuePairs.keySet());

		for (int i = 0; i < keys.size(); i++) {
			String key = keys.get(i);
			println((i + 1) + " " + key + " - " + printFormatter.apply(keyValuePairs.get(key)));
		}

		prompt(prompt);
		int startpointIndex = scanner.nextInt();
		return keys.get(startpointIndex - 1);
	}

	private void print(String s) {
		if (highlightPrinting) System.out.print(highlightingColor + s + ANSI_RESET);
		else System.out.print(s);
	}

	private void println(String s) {
		if (highlightPrinting) System.out.println(highlightingColor + s + ANSI_RESET);
		else System.out.println(s);
	}

	public boolean isHighlightPrinting() {
		return highlightPrinting;
	}

	public void setHighlightPrinting(boolean highlightPrinting) {
		this.highlightPrinting = highlightPrinting;
	}

	public String getHighlightingColor() {
		return highlightingColor;
	}

	public void setHighlightingColor(String highlightingColor) {
		this.highlightingColor = highlightingColor;
	}

	public static CLI getInstance() {
		return instance;
	}
}
