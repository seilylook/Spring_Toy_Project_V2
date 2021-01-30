package Registration.registration.domain.item;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;

@Entity
@DiscriminatorColumn("V")
@Getter @Setter
public class Vehicle extends Item {


}
