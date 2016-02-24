package org.segrada.model.util;

import java.util.EnumSet;

/**
 * Copyright 2015 Maximilian Kalus [segrada@auxnet.de]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Fuzzy flag implementation for fuzzy dates
 */
public enum FuzzyFlag {
	FUZZY_CA('c'), // ca prefix
	FUZZY_UNKNOWN('?'); // ? suffix

	private final char fuzzyValue;

	FuzzyFlag(char fuzzyValue) {
		this.fuzzyValue = fuzzyValue;
	}

	public char getFuzzyValue() {
		return fuzzyValue;
	}

	/**
	 * translate char to fuzzy flag
	 * @param flag input char
	 * @return corresponding flag or null
	 */
	public static FuzzyFlag translateCharToFuzzyFlag(char flag) {
		switch (flag) {
			case 'c': return FUZZY_CA;
			case '?': return FUZZY_UNKNOWN;
			default: break;
		}
		return null;
	}

	/**
	 * helper to add to any container
	 * @param flag to set
	 * @param flagContainer to be set
	 * @return changed container
	 */
	public static EnumSet<FuzzyFlag> addFuzzyFlag(char flag, EnumSet<FuzzyFlag> flagContainer) {
		FuzzyFlag f = FuzzyFlag.translateCharToFuzzyFlag(flag);
		if (f != null) {
			// null container? return new one
			if (flagContainer == null) return EnumSet.of(f);
			// otherwise just add to set
			flagContainer.add(f);
		}
		return flagContainer;
	}

	/**
	 * helper to delete from any container
	 * @param flag to delete
	 * @param flagContainer to be set
	 * @return changed container
	 */
	public static EnumSet<FuzzyFlag> deleteFuzzyFlag(char flag, EnumSet<FuzzyFlag> flagContainer) {
		FuzzyFlag f = FuzzyFlag.translateCharToFuzzyFlag(flag);
		if (f != null && flagContainer != null) {
			// remove from container set
			flagContainer.remove(f);
			// all entries removed, remove container
			if (flagContainer.isEmpty()) return null;
		}
		return flagContainer;
	}

	/**
	 * helper to check if flag is contained in container
	 * @param flag to check
	 * @param flagContainer to be checked
	 * @return true, if flag is in container
	 */
	public static boolean hasFuzzyFlag(char flag, EnumSet<FuzzyFlag> flagContainer) {
		FuzzyFlag f = FuzzyFlag.translateCharToFuzzyFlag(flag);
		return f != null && flagContainer != null && flagContainer.contains(f);
	}

	/**
	 * helper to get all flags from container
	 * @param flagContainer to be extracted
	 * @return list of flags, can be empty array
	 */
	public static char[] getFuzzyFlags(EnumSet<FuzzyFlag> flagContainer) {
		// empty? return empty
		if (flagContainer == null || flagContainer.isEmpty()) return new char[0];
		char[] values = new char[flagContainer.size()];
		int i = 0;
		for (FuzzyFlag fuzzyFlag : flagContainer) {
			values[i++] = fuzzyFlag.getFuzzyValue();
		}

		return values;
	}
}
