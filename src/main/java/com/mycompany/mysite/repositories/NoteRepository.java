package com.mycompany.mysite.repositories;

import com.mycompany.mysite.model.Note;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {

    public List<Note> findByAuthor_Id(Long id);

    @Modifying
    @Query("delete from Note n where n.id = :id")
    public void deleteById(@Param("id") Long id);

}
