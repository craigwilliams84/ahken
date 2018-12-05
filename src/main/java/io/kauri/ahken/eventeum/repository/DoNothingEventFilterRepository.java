package io.kauri.ahken.eventeum.repository;

import java.util.Collections;
import java.util.Optional;
import net.consensys.eventeum.dto.event.filter.ContractEventFilter;
import org.springframework.data.repository.CrudRepository;

/**
 * As we load from the scripts folder on every startup, there's no need to store
 * registered filters in a database.  This repository does nothing.
 *
 * @author Craig Williams
 */
public class DoNothingEventFilterRepository implements CrudRepository<ContractEventFilter, String> {

    @Override
    public ContractEventFilter save(ContractEventFilter s) {
        return null;
    }

    @Override
    public <S extends ContractEventFilter> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<ContractEventFilter> findById(String s) {
        return null;
    }

    @Override
    public boolean existsById(String s) {
        return false;
    }

    @Override
    public Iterable<ContractEventFilter> findAll() {
        return Collections.emptySet();
    }

    @Override
    public Iterable<ContractEventFilter> findAllById(Iterable<String> strings) {
        return Collections.emptySet();
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(String s) {

    }

    @Override
    public void delete(ContractEventFilter contractEventFilter) {

    }

    @Override
    public void deleteAll(Iterable<? extends ContractEventFilter> entities) {

    }

    @Override
    public void deleteAll() {

    }
}
