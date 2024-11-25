package com.mohammedsaqibkhan.recipeservice.mapper;

import com.mohammedsaqibkhan.recipeservice.dto.NutritionalInfoDTO;
import com.mohammedsaqibkhan.recipeservice.entity.NutritionalInfo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NutritionalInfoMapper {

    NutritionalInfoDTO toDTO(NutritionalInfo nutritionalInfo);

    NutritionalInfo toEntity(NutritionalInfoDTO nutritionalInfoDTO);
}
