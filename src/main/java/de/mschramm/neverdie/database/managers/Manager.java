package de.mschramm.neverdie.database.managers;

public abstract class Manager<T, K> {

    public abstract void save(T entity) throws Exception;
    public abstract T getByKey(K key) throws Exception;
    public abstract T[] getAll() throws Exception;

}
