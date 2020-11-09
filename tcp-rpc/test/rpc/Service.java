package rpc;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Service implements Contract {
	
	private Map<String, Note> notes;
	
	public Service() {
		notes = new HashMap<String, Note>();
	}

	@Override
	public Collection<Note> getNotes() {
		return Collections.unmodifiableCollection(notes.values());
	}

	@Override
	public Optional<Note> getNote(String id) {
		return id != null && notes.containsKey(id)?
				Optional.of(notes.get(id)) : Optional.empty();
	}

	@Override
	public Optional<Note> addNote(String title, String content) {
		Note note = null;
		if (title != null && content != null) {
			note = new Note(title, content);
			notes.put(note.getId(), note);
			return Optional.of(note);
		} else {
			return Optional.empty();
		}
	}

	@Override
	public Optional<Note> changeNote(String id, String title, String content) {
		if (id != null
				&& title != null
				&& content != null && notes.containsKey(id)) {
			Note note = notes.get(id);
			note.setTitle(title);
			note.setContent(content);
			return Optional.of(note);
		} else {
			return Optional.empty();
		}
	}

	@Override
	public boolean removeNote(String id) {
		if (id != null && notes.containsKey(id)) {
			notes.remove(id);
			return true;
		} else {
			return false;
		}
	}

}
