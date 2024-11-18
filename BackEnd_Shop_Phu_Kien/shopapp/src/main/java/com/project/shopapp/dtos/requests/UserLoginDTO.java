package com.project.shopapp.dtos.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserLoginDTO {

    @JsonProperty("phone_number")
    @NotBlank(message = "Phone number is required")
    String phoneNumber;

    @NotBlank(message = "Password is required")
    @JsonProperty("password")
    String passWord;


}
