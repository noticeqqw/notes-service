package noticeqqw.project.main.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import noticeqqw.project.main.dto.note.NoteRequest;
import noticeqqw.project.main.dto.note.NoteResponse;
import noticeqqw.project.main.service.NoteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notes")
@RequiredArgsConstructor
public class NoteController {

    private final NoteService noteService;

    @GetMapping
    public ResponseEntity<List<NoteResponse>> getNotes(@RequestParam(required = false) String tag) {
        return ResponseEntity.ok(noteService.getAllNotes(tag));
    }

    @PostMapping
    public ResponseEntity<NoteResponse> createNote(@Valid @RequestBody NoteRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(noteService.createNote(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<NoteResponse> updateNote(@PathVariable Long id,
                                                   @Valid @RequestBody NoteRequest request) {
        return ResponseEntity.ok(noteService.updateNote(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNote(@PathVariable Long id) {
        noteService.deleteNote(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/tags")
    public ResponseEntity<NoteResponse> addTag(@PathVariable Long id, @RequestParam String name) {
        return ResponseEntity.ok(noteService.addTag(id, name));
    }
}
