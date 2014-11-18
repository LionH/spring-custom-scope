package org.tesolin.scope.scoped;

import java.util.Collection;

public interface Call {
	
	void addWord(String word);

	Collection<String> words();
}