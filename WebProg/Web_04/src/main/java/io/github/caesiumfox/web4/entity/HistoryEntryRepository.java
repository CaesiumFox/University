package io.github.caesiumfox.web4.entity;

import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface HistoryEntryRepository extends CrudRepository<HistoryEntry, Long> {
    public List<HistoryEntry> findAll();
}
