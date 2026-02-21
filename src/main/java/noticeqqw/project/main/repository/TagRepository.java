package noticeqqw.project.main.repository;

import noticeqqw.project.main.entity.Tag;
import noticeqqw.project.main.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {
    Optional<Tag> findByNameAndUser(String name, User user);
    List<Tag> findByUser(User user);
}
