package noticeqqw.project.main.dto.note;

import noticeqqw.project.main.entity.Note;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

public record NoteResponse(
    Long id,
    String title,
    String content,
    LocalDateTime createdAt,
    LocalDateTime updatedAt,
    Set<String> tags
) {
    public static NoteResponse from(Note note) {
        return new NoteResponse(
            note.getId(),
            note.getTitle(),
            note.getContent(),
            note.getCreatedAt(),
            note.getUpdatedAt(),
            note.getTags().stream().map(t -> t.getName()).collect(Collectors.toSet())
        );
    }
}
