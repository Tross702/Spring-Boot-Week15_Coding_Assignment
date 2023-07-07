package pet.store.controller.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PetStoreEmployee {
    private Long id;
    private String employeeName;

    public PetStoreEmployee(Long id, String employeeName) {
        this.id = id;
        this.employeeName = employeeName;
    }
}
