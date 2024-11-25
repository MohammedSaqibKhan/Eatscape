package com.mohammedsaqibkhan.recipeservice.mapper;

import com.mohammedsaqibkhan.recipeservice.dto.DietTypeDTO;
import com.mohammedsaqibkhan.recipeservice.entity.DietType;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DietTypeMapper {

    DietTypeDTO toDTO(DietType dietType);

    DietType toEntity(DietTypeDTO dietTypeDTO);
}