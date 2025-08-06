package com.fondationsociale.auth.transformer;

import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractTransformer<E, GetDto, PostDto> {
    
    public abstract GetDto entityToGetDto(E entity);
    
    public abstract E postDtoToEntity(PostDto postDto);
    
    public abstract void updateEntityFromPostDto(PostDto postDto, E entity);
    
    public List<GetDto> entitiesToGetDtos(List<E> entities) {
        return entities.stream()
                .map(this::entityToGetDto)
                .collect(Collectors.toList());
    }
    
    public List<E> postDtosToEntities(List<PostDto> postDtos) {
        return postDtos.stream()
                .map(this::postDtoToEntity)
                .collect(Collectors.toList());
    }
}