package com.meraphorce.mappers;

public interface MapperI<T, RS, RQ>
{
    public T responseToEntity(RS response);
    public T requestToEntity(RQ request);
    public RS entityToResponse(T entity);
    public RQ entityToRequest(T entity);
}
