package com.example.user_management_service.mapper;

import com.example.user_management_service.model.Country;
import com.example.user_management_service.dto.CountryDTO;
import com.example.user_management_service.util.DateTimeUtil;
import org.springframework.stereotype.Component;

@Component
public class CountryMapper implements IMapper<Country, CountryDTO>{

    @Override
    public CountryDTO toDto(Country entity) {
        if (entity == null) {
            return null;
        }
        return new CountryDTO(
                entity.getId(),
                entity.getName(),
                entity.getAbbreviation(),
                DateTimeUtil.convertToString(entity.getCreatedAt()),
                DateTimeUtil.convertToString(entity.getUpdatedAt()),
                entity.getIcon(),
                entity.getIsDeleted()
        );
    }


    @Override
    public Country toEntity(CountryDTO dto) {
        if (dto == null) {
            return null;
        }
        Country country = new Country();

        country.setId(dto.getId());
        country.setName(dto.getName());
        country.setAbbreviation(dto.getAbbreviation());

        country.setCreatedAt(DateTimeUtil.parseIsoString(dto.getCreatedAt()));
        country.setUpdatedAt(DateTimeUtil.parseIsoString(dto.getUpdatedAt()));
        country.setIcon(dto.getIcon());
        country.setIsDeleted(dto.getIsDeleted());

        return country;
    }
}
