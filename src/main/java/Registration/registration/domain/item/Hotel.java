package Registration.registration.domain.item;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;

@Entity
@DiscriminatorColumn("H")
@Getter @Setter
public class Hotel extends Item {


}
