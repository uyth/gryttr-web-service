package com.uyth.gryttr.model.assemblers;

import com.uyth.gryttr.model.Collection;
import com.uyth.gryttr.model.dto.CollectionCreationDto;
import com.uyth.gryttr.model.dto.CollectionResponseDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
public class CollectionAssembler {

    public CollectionResponseDto mapToDto(Collection collection) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(collection, CollectionResponseDto.class);
    }

    public Collection mapToCollection(CollectionCreationDto collectionDto) {
        Collection collection = new Collection();
        collection.setName(collectionDto.getName());
        collection.setBoulders(new HashSet<>());
        return collection;
    }
}
