package Registration.registration.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")

    private Long id;
    private String username;

    @Embedded
    private Address address;

    //why one to many? 한 회원이 다수의 상품을 조회할 수 있어야 되기 때문이다.
    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();

}
