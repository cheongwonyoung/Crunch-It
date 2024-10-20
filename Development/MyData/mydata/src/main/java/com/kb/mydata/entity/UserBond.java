package com.kb.mydata.entity;
import com.kb.mydata.entity.serializable.UserBondId;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@IdClass(UserBondId.class)
@Table(name = "user_bond")
@Getter
public class UserBond {
    @Id
    @Column(name = "bond_code")
    private String bondCode;

    @Id
    @Column(name = "user_id", insertable = false, updatable = false) // 복합 키에 포함된 userId 필드
    private int userId;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    private MdUser user;

    @Column(name = "bond_name")
    private String bondName;

    @Column(name = "average_purchase_price")
    private int averagePurchasePrice;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "interest_rate")
    private int interestRate;

    // Getters and Setters
}