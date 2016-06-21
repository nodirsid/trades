/**
 * Created by Nodirjon on 6/21/2016.
 */
public class Trade {
    private int tradeID;
    private int userID;
    private String bs;
    private int quantity;
    private String instrument;
    private double price;
    public enum BuyOrSell{B, S};

    //default constructor
    public Trade(){
        this.price = 0;
        this.instrument = "";
        this.quantity = 0;
        this.bs = null;
        this.userID = 0;
        this.tradeID = 0;
    }

    //overloaded constructor
    public Trade(
            int tradeID,
            int userID,
            String bs,
            int quantity,
            String instrument,
            double price
            ) {

        this.price = price;
        this.instrument = instrument;
        this.quantity = quantity;
        this.bs = bs;
        this.userID = userID;
        this.tradeID = tradeID;
    }


    //getters
    public int getTradeID() {
        return tradeID;
    }

    public int getUserID() {
        return userID;
    }

    public String getBs() {
        return bs;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getInstrument() {
        return instrument;
    }

    public double getPrice() {
        return price;
    }


    //setters
    public void setTradeID(int tradeID) {
        this.tradeID = tradeID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void setBs(String bs) {
        this.bs = bs;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setInstrument(String instrument) {
        this.instrument = instrument;
    }

    public void setPrice(double price) {
        this.price = price;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Trade other = (Trade) obj;
        if (tradeID != other.tradeID)
            return false;
        return true;
    }
}
