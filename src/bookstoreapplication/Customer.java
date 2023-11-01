package bookstoreapplication;

public class Customer {

    private String username;
    private String password;
    private int points = 0;

    public Customer(final String username, final String password, final int points) {
        this.username = username;
        this.password = password;
	this.points = points;
    }

    public String getUserName() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void buyBook(Book book){
        points += (book.getPrice() * 10);
    }

    public int getStatus() {
        if (points > 1000)
            return 1;   //gold
        return 0;       //silver
    }

    public int getPoints() {
		return points;
    }
    
    public void addPoints(int p) {
        points += p;
        if (points < 0)
            points = 0;
    }
}