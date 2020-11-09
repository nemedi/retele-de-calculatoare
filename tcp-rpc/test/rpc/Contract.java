package rpc;

import java.util.Collection;
import java.util.Optional;

public interface Contract {
	
	Collection<Note> getNotes();

	Optional<Note> getNote(String id);

	Optional<Note> addNote(String title, String content);

	Optional<Note> changeNote(String id, String title, String content);

	boolean removeNote(String id);
}
