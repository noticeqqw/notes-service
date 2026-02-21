package noticeqqw.project.main.repository;

import noticeqqw.project.main.entity.Note;
import noticeqqw.project.main.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NoteRepository extends JpaRepository<Note, Long> {
    List<Note> findByUser(User user);
    List<Note> findByUserAndTags_Name(User user, String tagName);
    Optional<Note> findByIdAndUser(Long id, User user);
}
