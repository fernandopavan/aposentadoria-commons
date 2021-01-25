package com.aposentadoria.domain.pattern;

public abstract class EntityBuilder<T> implements IBuilder<T> {

    protected T entity;
    protected State state;

    public EntityBuilder(T entity, State state) {
        this.entity = entity;
        this.state = state;
    }

    @Override
    public T build() {
        return entity;
    }

    protected enum State {
        NEW, BUILT;

        State() {
        }
    }

}
