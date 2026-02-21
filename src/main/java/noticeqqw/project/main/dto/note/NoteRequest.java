package noticeqqw.project.main.dto.note;

import jakarta.validation.constraints.NotBlank;

public record NoteRequest(
    @NotBlank String title,
    String content
) {}
