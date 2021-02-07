package Registration.registration.domain;

public class OrderSearch {

    private String memberName;
    private OrderStatus orderStatus;

    public String getMemberName() {
        return memberName;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }
}
