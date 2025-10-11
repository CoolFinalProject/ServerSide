package server.convertions;

import server.DTO.UserDto.UserDto;
import server.entities.UserEntities.UserEntity;

public class UserConvertion {


    public static UserEntity userDtoToEntity(UserDto dto)
    {
        // String userName, String passWord, String userId, boolean active, UserRole userRole
        return new UserEntity(dto.getUserName(),dto.getPassWord(),dto.getUserId(),dto.isActive(),dto.getUserRole(),dto.getCreationTime());
    }
    public static UserDto userEntityToDto(UserEntity entity)
    {
        return new UserDto(entity.getUserName(),entity.getPassWord(),entity.getUserId(),entity.isActive(),entity.getUserRole(),entity.getCreationTime());
    }
}
