package com.fondationsociale.auth.service;

import java.util.List;
import java.util.Optional;

public abstract class AbstractService<E, GetDto, PostDto, ID> {
    
    public abstract List<GetDto> findAll();
    
    public abstract Optional<GetDto> findById(ID id);
    
    public abstract GetDto save(PostDto postDto);
    
    public abstract Optional<GetDto> update(ID id, PostDto postDto);
    
    public abstract boolean deleteById(ID id);
    
    public abstract boolean existsById(ID id);
    
    public abstract long count();
}