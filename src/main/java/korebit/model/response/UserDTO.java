package korebit.model.response;

import korebit.model.beans.RefreshToken;
import korebit.model.beans.User;
import korebit.model.enums.Role;

import java.io.Serializable;

public record UserDTO(
        String name,
        String username,
        String pass,
        String email,
        String phone,
        String address,
        Role role,
        RefreshToken token
) implements Serializable {
    public static UserDTO toDTO(User user) {
        return new UserDTO(
                user.getName(),
                user.getUsername(),
                user.getPassword(),
                user.getEmail(),
                user.getPhone(),
                user.getAddress(),
                user.getRole(),
                user.getRefreshToken()
        );
    }

    @Deprecated
    public static User toEntity(UserDTO userDTO) {
        return new User(
                0L,
                userDTO.name,
                userDTO.username,
                userDTO.pass,
                userDTO.email,
                userDTO.phone,
                userDTO.address,
                userDTO.role,
                userDTO.token
        );
    }
}
