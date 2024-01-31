package at.kaindorf.bank.pojos;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Address {
    @Id
    @Column(name = "address_id")
    @NonNull
    private Long addressId;
    @Column(length = 100)
    @NonNull
    private String streetname;
    @JsonAlias("streetNumber")
    @Column(name = "street_number", length = 100)
    @NonNull
    private String steetNumber;
    @Column(name = "zip_code", length = 100)
    @NonNull
    private String zipCode;
    @Column(length = 100)
    @NonNull
    private String city;
}
