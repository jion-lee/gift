package phoneseller;

import javax.persistence.*;
import org.springframework.beans.BeanUtils;
import java.util.List;

@Entity
@Table(name="Gift_table")
public class Gift {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private Long orderId;
    private Double point;
    private String process;

    @PrePersist
    public void onPrePersist() {
        System.out.println("gift pre persist");
    }

    @PostPersist
    public void onPostPersist(){
        System.out.println(this.toString());
        System.out.println("gift persist");

        if("Payed".equals(process) && point > 0){
            // 결제 완료된 이벤트를 통해 프로모션 제공 완료 처리

            GiftCompleted giftCompleted = new GiftCompleted();
            BeanUtils.copyProperties(this, giftCompleted);
            giftCompleted.publish();

            System.out.println("*** 프로모션 포인트 제공 완료 ***");
        } else if("PayCancelled".equals(process)){
            GiftCancelled giftCancelled = new GiftCancelled();
            BeanUtils.copyProperties(this, giftCancelled);
            giftCancelled.publish();
            System.out.println("*** 결제 취소로 인한 프로모션 포인트 제공 회수 ***");
        }
    }

    @PreUpdate
    public void onPreUpdate(){
        System.out.println("gift pre update");
    }

    @PostUpdate
    public void onPostUpdate(){
        System.out.println("gift post update");
    }

    @PreRemove
    public void onPreRemove(){
        System.out.println("gift pre remove");
    }

    @PostRemove
    public void onPostRemove(){
        System.out.println("gift post remove");
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
    public Double getPoint() {
        return point;
    }

    public void setPoint(Double point) {
        this.point = point;
    }

    public String getProcess() {
        return process;
    }

    public void setProcess(String process) {
        this.process = process;
    }

    @Override
    public String toString() {
        return "Gift{" +
                "id=" + id +
                ", orderId=" + orderId +
                ", point=" + point +
                ", process=" + process +
                '}';
    }
}
