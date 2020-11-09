package notes;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/notes")
public class NoteController {

	@Autowired
	private NoteRepository repository;
	
	@Operation(summary = "Get all notes", operationId = "getNotes")
    @ApiResponses({ 
            @ApiResponse(responseCode = "200", description = "Found notes", 
                content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Note[].class))}),
            @ApiResponse(responseCode = "204", description = "No notes found", content = @Content)})	
	@GetMapping
	public ResponseEntity<List<Note>> getNotes() {
		List<Note> notes = (List<Note>) repository.findAll();
		return !notes.isEmpty()
				? ResponseEntity.ok(notes)
				: ResponseEntity.noContent().build();
	}
	
	@Operation(summary = "Get a note by its id", operationId = "getNote")
    @ApiResponses({ 
            @ApiResponse(responseCode = "200", description = "Found the note",
            	content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Note.class))}),
            @ApiResponse(responseCode = "404", description = "Note not found", content = @Content)})	
	@GetMapping("/{id}")
	public ResponseEntity<Note> getNote(@PathVariable long id) {
		Optional<Note> note = repository.findById(Long.valueOf(id));
		return note.isPresent()
				? ResponseEntity.ok(note.get())
				: ResponseEntity.notFound().build();
	}
	
	@Operation(summary = "Create a new note", operationId = "addNote")
    @ApiResponses({ 
            @ApiResponse(responseCode = "201", description = "Note was created", content = @Content),
            @ApiResponse(responseCode = "500", description = "Something went wrong", content = @Content)})
	@PostMapping
	public ResponseEntity<Note> addNote(@RequestBody Note note) {
		try {
			note = repository.save(note);
			URI uri = linkTo(NoteController.class).slash(note.getId()).toUri();
			return ResponseEntity.created(uri).build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@Operation(summary = "Update a note by its id", operationId = "changeNote")
    @ApiResponses({ 
            @ApiResponse(responseCode = "204", description = "Note was update", content = @Content),
            @ApiResponse(responseCode = "404", description = "Note not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Something went wrong", content = @Content)})
	@PutMapping("/{id}")
	public ResponseEntity<Void> changeNote(@PathVariable long id, @RequestBody Note note) {
		Optional<Note> existingNpote = repository.findById(Long.valueOf(id));
		if (existingNpote.isPresent()) {
			try {
				existingNpote.get().setTitle(note.getTitle());
				existingNpote.get().setContent(note.getContent());
				repository.save(existingNpote.get());
				return ResponseEntity.noContent().build();
			} catch (Exception e) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			}
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@Operation(summary = "Delete a note by its id", operationId = "removeNote")
    @ApiResponses({ 
            @ApiResponse(responseCode = "204", description = "Note was deleted", content = @Content),
            @ApiResponse(responseCode = "404", description = "Note not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Something went wrong", content = @Content)})
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> removeNote(@PathVariable long id) {
		if (repository.existsById(Long.valueOf(id))) {
			try {
				repository.deleteById(Long.valueOf(id));
				return ResponseEntity.noContent().build();
			} catch (Exception e) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			}
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}
