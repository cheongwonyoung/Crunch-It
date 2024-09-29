package entity;

import lombok.Getter;

@Getter
public class Bond {
    public String bond_code;
    public int user_id;
    public String bond_name;
    public int average_purchase_price;
    public int quantity;
    public float interest_rate;
}
