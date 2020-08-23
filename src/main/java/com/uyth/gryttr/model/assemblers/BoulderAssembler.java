package com.uyth.gryttr.model.assemblers;

import com.uyth.gryttr.model.Boulder;
import com.uyth.gryttr.model.dto.BoulderCreationDto;
import com.uyth.gryttr.model.dto.BoulderResponseDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class BoulderAssembler {

    public BoulderResponseDto mapToDto(Boulder boulder) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(boulder, BoulderResponseDto.class);
    }

    public Boulder mapToBoulder(BoulderCreationDto boulder) {
        Boulder newBoulder = new Boulder();
        newBoulder.setName(boulder.getName());
        newBoulder.setGrade(boulder.getGrade());
        newBoulder.setLatLong(boulder.getLatitude(), boulder.getLongitude());
        return newBoulder;
    }
}
