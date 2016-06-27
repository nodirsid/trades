import java.util.Comparator;

public class Trade {
    private int tradeID;
    private int userID;
    private String buyOrSell;
    private int quantity;
    private String instrument;
    private double price;

    //default constructor
    public Trade(){
        this.price = 0;
        this.instrument = "";
        this.quantity = 0;
        this.buyOrSell = null;
        this.userID = 0;
        this.tradeID = 0;
    }

    //overloaded constructor
    public Trade(
            int tradeID,
            int userID,
            String buyOrSell,
            int quantity,
            String instrument,
            double price
            ) {

        this.price = price;
        this.instrument = instrument;
        this.quantity = quantity;
        this.buyOrSell = buyOrSell;
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

    public String getBuyOrSell() {
        return buyOrSell;
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

    public void setBuyOrSell(String buyOrSell) {
        this.buyOrSell = buyOrSell;
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

    //comparator for sorting the list by trade id
    public static Comparator<Trade> tradeIdComparator = new Comparator<Trade>() {

        public int compare(Trade t1, Trade t2) {
            int tradeId1 = t1.getTradeID();
            int tradeId2 = t2.getTradeID();

            //ascending order by default
            return tradeId1 - tradeId2;
        }
    };

    //comparator for sorting the list by user id
    public static Comparator<Trade> userIdComparator = new Comparator<Trade>() {

        public int compare(Trade t1, Trade t2) {
            int userId1 = t1.getUserID();
            int userId2 = t2.getUserID();

            //ascending order by default
            return userId1 - userId2;
        }
    };

    //comparator for sorting the list by quantity
    public static Comparator<Trade> qtyComparator = new Comparator<Trade>() {

        public int compare(Trade t1, Trade t2) {
            int qty1 = t1.getQuantity();
            int qty2 = t2.getQuantity();

            //ascending order by default
            return qty1 - qty2;
        }
    };

    //comparator for sorting the list by price
    public static Comparator<Trade> priceComparator = new Comparator<Trade>() {

        public int compare(Trade t1, Trade t2) {
            double price1 = t1.getPrice();
            double price2 = t2.getPrice();

            //ascending order by default
            if(price1 < price2) return -1;
            if(price1 > price2) return 1;
            return 0;
        }
    };

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

    @Override
    public String toString() {
        return getTradeID()+","+getUserID()+","+getBuyOrSell()+","+getQuantity()+","+getInstrument()+","+getPrice();
    }
}
