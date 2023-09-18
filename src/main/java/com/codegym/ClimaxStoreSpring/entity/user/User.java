package com.codegym.ClimaxStoreSpring.entity.user;

import com.codegym.ClimaxStoreSpring.entity.business.BoughtProduct;
import com.codegym.ClimaxStoreSpring.entity.business.CartDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "user_name", nullable = false, unique = true)
    private String userName;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "phone_number", nullable = false, unique = true)
    private String phoneNumber;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "is_deleted")
    private Boolean deleted = false;

    @Column(name = "created_date")
    @Temporal(TemporalType.DATE)
    private Date createdDate = new Date();

    @ManyToOne(targetEntity = Role.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", referencedColumnName = "role_id")
    private Role role;

    @OneToOne(targetEntity = UserDetail.class, fetch = FetchType.LAZY,
            cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "user_detail_id", referencedColumnName = "user_detail_id")
    private UserDetail userDetail;

    @OneToOne(targetEntity = Address.class, fetch = FetchType.EAGER,
            cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "address_id", referencedColumnName = "address_id")
    private Address address;

    @OneToMany(targetEntity = CartDetail.class, fetch = FetchType.LAZY, mappedBy = "user",
            cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private List<CartDetail> cartDetailList;

    @OneToMany(targetEntity = BoughtProduct.class, fetch = FetchType.LAZY, mappedBy = "user",
            cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private List<BoughtProduct> boughtProductList;
}
