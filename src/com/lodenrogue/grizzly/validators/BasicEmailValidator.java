package com.lodenrogue.grizzly.validators;

public class BasicEmailValidator implements TextValidator {

	@Override
	public boolean isValid(String text) {
		if (text.contains("@")) {
			if (isAtValid(text)) {

				String[] textSplit = text.split("@");
				String name = textSplit[0];
				String domain = textSplit[1];

				if (isNameValid(name) && isDomainValid(domain)) {
					return true;
				}
				else return false;
			}
			else return false;
		}
		return false;
	}

	private boolean isDomainValid(String text) {
		return true;
	}

	private boolean isNameValid(String text) {
		return true;
	}

	private boolean isAtValid(String text) {
		int count = repeatCount(text, '@');

		if (count > 1) {
			return false;
		}

		return true;
	}

	private int repeatCount(String text, char query) {

		int count = 0;
		for (int i = 0; i < text.length(); i++) {
			if (text.charAt(i) == query) {
				count++;
			}
		}

		return count;
	}
}
