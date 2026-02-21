package noticeqqw.project.main.service;

import lombok.RequiredArgsConstructor;
import noticeqqw.project.main.dto.note.NoteRequest;
import noticeqqw.project.main.dto.note.NoteResponse;
import noticeqqw.project.main.entity.Note;
import noticeqqw.project.main.entity.Tag;
import noticeqqw.project.main.entity.User;
import noticeqqw.project.main.repository.NoteRepository;
import noticeqqw.project.main.repository.TagRepository;
import noticeqqw.project.main.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoteService {

    private final NoteRepository noteRepository;
    private final UserRepository userRepository;
    private final TagRepository tagRepository;

    private User getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username).orElseThrow();
    }

    @Transactional(readOnly = true)
    public List<NoteResponse> getAllNotes(String tagName) {
        User user = getCurrentUser();
        List<Note> notes = (tagName != null)
            ? noteRepository.findByUserAndTags_Name(user, tagName)
            : noteRepository.findByUser(user);
        return notes.stream().map(NoteResponse::from).toList();
    }

    @Transactional
    public NoteResponse createNote(NoteRequest request) {
        User user = getCurrentUser();
        Note note = new Note();
        note.setTitle(request.title());
        note.setContent(request.content());
        note.setUser(user);
        return NoteResponse.from(noteRepository.save(note));
    }

    @Transactional
    public NoteResponse updateNote(Long id, NoteRequest request) {
        User user = getCurrentUser();
        Note note = noteRepository.findByIdAndUser(id, user)
            .orElseThrow(() -> new RuntimeException("Note not found"));
        note.setTitle(request.title());
        note.setContent(request.content());
        return NoteResponse.from(noteRepository.save(note));
    }

    @Transactional
    public void deleteNote(Long id) {
        User user = getCurrentUser();
        Note note = noteRepository.findByIdAndUser(id, user)
            .orElseThrow(() -> new RuntimeException("Note not found"));
        noteRepository.delete(note);
    }

    @Transactional
    public NoteResponse addTag(Long noteId, String tagName) {
        User user = getCurrentUser();
        Note note = noteRepository.findByIdAndUser(noteId, user)
            .orElseThrow(() -> new RuntimeException("Note not found"));

        Tag tag = tagRepository.findByNameAndUser(tagName, user)
            .orElseGet(() -> {
                Tag newTag = new Tag();
                newTag.setName(tagName);
                newTag.setUser(user);
                return tagRepository.save(newTag);
            });

        note.getTags().add(tag);
        return NoteResponse.from(noteRepository.save(note));
    }
}
