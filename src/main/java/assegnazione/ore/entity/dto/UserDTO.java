package assegnazione.ore.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {

    private String name;
    private String lastName;
    private String nameMonth;

    public UserDTO(String name, String lastName) {
        this.name = name;
        this.lastName = lastName;
    }



}
