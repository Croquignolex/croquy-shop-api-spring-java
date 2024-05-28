package com.shop.croquy.v1.dto.backoffice.user;

import com.shop.croquy.v1.entities.User;
import com.shop.croquy.v1.enums.Gender;
import com.shop.croquy.v1.enums.Role;
import com.shop.croquy.v1.helpers.GeneralHelper;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UserStoreRequest {
    @NotEmpty(message = "Username field is required")
    protected String username;

    @Email(message = "The email address is invalid", flags = { Pattern.Flag.CASE_INSENSITIVE })
    protected String email;

    @NotEmpty(message = "Password field is required")
    protected String password;

    @NotEmpty(message = "First name field is required")
    protected String firstName;

    protected String lastName;

    protected String profession;

    protected String gender;

    @NotEmpty(message = "Role field is required")
    protected String role;

    protected String birthdate;

    protected String description;

    public User toUser(User creator) {
        User user = new User();

        if(gender != null) user.setGender(Gender.getEnumFromString(gender));

        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setProfession(profession);
        user.setRole(Role.getEnumFromString(role));
        user.setBirthdate( GeneralHelper.textToDate(birthdate).orElse(null));
        user.setDescription(description);
        user.setCreator(creator);

        return user;
    }
}