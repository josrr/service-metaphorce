package com.meraphorce.mappers;

/**
 * Mapper interface to translate between entities objects and DTO objects.
 */
public interface MapperI<T, RS, RQ>
{
    /**
     * Translates from the request DTO to the entity object.
     *
     * @param request a request DTO
     * @return an entity object
     */
    public T requestToEntity(RQ request);

    /**
     * Translates from the entity object to the response DTO.
     *
     * @param entity an entity object
     * @return a response DTO
     */
    public RS entityToResponse(T entity);
}
