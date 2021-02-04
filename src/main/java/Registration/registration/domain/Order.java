package Registration.registration.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter @Setter
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    // 회원과 반대로 여러개의 상품은 하나의 회원에만 연결되어야 한다.
    // 이를 구분해주기 위해 member_id를 토대로 조회한다.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    // 하나의 상품은 하나의 배송정보를 가지기 때문에 one to one 이다.
    // 또한 이를 정확하게 조회하기 위해 delivery_id를 사용한다.
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate;

    // 주문 상태는 order, cancel 두가지로 구분하기 위해 enum
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    public void setMember(Member member) {
        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    // 주문을 생성한다.
    // parameter로 받아야 될 것은 회원 정보, 배송정보, 상품 들
    // order 객체를 생성해주고 order 객체의 메소드들을 통해 회원 배송정보 상품 정보를 저장한다.
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems) {
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        for(OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }
        order.setStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());
        return order;
    }

    // 취소를 통해 들어오면 배송 정보를 구해온다. delivery status 의 상태가 comp 상태면
    // 상품이 이미 예약 된 것이기에 취소가 불가능하다.
    public void cancel() {
        if(delivery.getStatus() == DeliveryStatus.COMP) {
            throw new IllegalStateException("이미 배송완료된 상품은 취소가 불가능합니다.");
        }

        this.setStatus(OrderStatus.CANCEL);
        for (OrderItem orderItem : orderItems) {
            orderItem.cancel();
        }
    }

    public int getTotalPrice() {
        int totalPrice =0;
        for (OrderItem orderItem : orderItems) {
            totalPrice += orderItem.getOrder().getTotalPrice();
        }
        return totalPrice;
    }
}
