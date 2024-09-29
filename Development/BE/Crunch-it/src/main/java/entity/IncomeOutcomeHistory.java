package entity;

import lombok.Getter;

@Getter
public class IncomeOutcomeHistory {
    public int income_outcome_history;
    public int account_id;
    public int user_id;
    public int transaction_type;
    public int amount;
    public String register_date;
}
