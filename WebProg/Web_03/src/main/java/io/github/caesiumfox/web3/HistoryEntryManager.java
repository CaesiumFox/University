package io.github.caesiumfox.web3;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HistoryEntryManager {
    private final SessionFactory sessionFactory;

    public HistoryEntryManager() {
        Configuration configuration = new Configuration().configure();
        configuration.addAnnotatedClass(HistoryEntry.class);
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties());
        sessionFactory = configuration.buildSessionFactory(builder.build());
    }

    public List<HistoryEntry> findHistoryEntries() {
        return sessionFactory.openSession()
                .createQuery("from HistoryEntry").list();
    }

    public void addNew(HistoryEntry entry) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(entry);
        transaction.commit();
        session.close();
    }
}
