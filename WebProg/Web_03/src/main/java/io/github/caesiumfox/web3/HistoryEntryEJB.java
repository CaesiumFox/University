package io.github.caesiumfox.web3;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class HistoryEntryEJB {
    @PersistenceContext
    private EntityManager entityManager;

    public List<HistoryEntry> findHistoryEntries() {
        TypedQuery<HistoryEntry> query = entityManager.createNamedQuery(
                HistoryEntry.namedQueryName, HistoryEntry.class);
        return query.getResultList();
    }

    public HistoryEntry addNew(HistoryEntry entry) {
        entityManager.persist(entry);
        return entry;
    }
}
