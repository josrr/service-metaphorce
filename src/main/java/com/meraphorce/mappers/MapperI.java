package com.meraphorce.mappers;

public interface MapperI<T, RS, RQ>
{
    public T requestToEntity(RQ request);
    public RS entityToResponse(T entity);
}
