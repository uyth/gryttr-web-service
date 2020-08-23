package com.uyth.gryttr.model.assemblers;

import com.uyth.gryttr.model.Boulder;
import com.uyth.gryttr.model.Collection;
import com.uyth.gryttr.model.dto.BoulderResponseDto;
import com.uyth.gryttr.model.dto.CollectionCreationDto;
import com.uyth.gryttr.model.dto.CollectionResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CollectionAssemblerTest {

    static final String NAME = "Collection";
    static final long ID = 1L;

    @Autowired
    CollectionAssembler collectionAssembler;

    @Test
    public void testMapToDto() {
        Collection collection = generateCollection();
        addBoulders(collection);
        CollectionResponseDto collectionDto = collectionAssembler.mapToDto(collection);

        assertEquals(collection.getId(), collectionDto.getId());
        assertEquals(collection.getName(), collectionDto.getName());
        assertEquals(collection.getBoulders().stream().map(Boulder::getId).collect(Collectors.toList()),
        collectionDto.getBoulders().stream().map(BoulderResponseDto::getId).collect(Collectors.toList()));
    }

    private Collection generateCollection() {
        Collection collection = new Collection();
        collection.setId(ID);
        collection.setName(NAME);

        return collection;
    }

    private void addBoulders(Collection collection) {
        HashSet<Boulder> boulders = new HashSet<>();
        boulders.add(generateEmptyBoulderWithId(1L, collection));
        boulders.add(generateEmptyBoulderWithId(4L, collection));
        boulders.add(generateEmptyBoulderWithId(6L, collection));
        boulders.add(generateEmptyBoulderWithId(8L, collection));
        collection.setBoulders(boulders);
    }

    private Boulder generateEmptyBoulderWithId(long id, Collection collection) {
        Boulder boulder = new Boulder();
        boulder.setId(id);
        boulder.setCollection(collection);
        return boulder;
    }

    @Test
    public void testMapToCollection() {
        CollectionCreationDto dto = new CollectionCreationDto();
        dto.setName(NAME);
        Collection collection = collectionAssembler.mapToCollection(dto);

        assertEquals(dto.getName(), collection.getName());
    }
}
