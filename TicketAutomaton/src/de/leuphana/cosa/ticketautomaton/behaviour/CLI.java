package de.leuphana.cosa.ticketautomaton.behaviour;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author Lennart_Admin
 */
public class CLI {
	private static CLI instance = new CLI();

	private Scanner scanner;

	private CLI() {
		scanner = new Scanner(System.in);
	}

	public void info(String s) {
		System.out.println(s);
	}

	public void prompt(String s) {
		System.out.println();
		System.out.print(s);
	}

	public void error(String s) {
		System.err.println(s);
	}

	/**
	 * Displays all options to the user and makes them select one.
	 * @param options The options the user should be shown
	 * @return the selected option
	 */
	public String displayAndSelect(Set<String> options, String prompt) {
		List<String> sps = List.copyOf(options);

		for (int i = 0; i < sps.size(); i++) {
			System.out.println((i + 1) + " " + sps.get(i));
		}

		prompt(prompt);
		int startpointIndex = scanner.nextInt();
		return sps.get(startpointIndex - 1);
	}

	public String displayAndSelect(Map<String, Object> keyValuePairs, String prompt, Function<Object, String> printFormatter) {
		List<String> keys = List.copyOf(keyValuePairs.keySet());

		for (int i = 0; i < keys.size(); i++) {
			String key = keys.get(i);
			System.out.println((i + 1) + " " + key + " - " + printFormatter.apply(keyValuePairs.get(key)));
		}

		prompt(prompt);
		int startpointIndex = scanner.nextInt();
		return keys.get(startpointIndex - 1);
	}

	public static CLI getInstance() {
		return instance;
	}
}
