package bookstoreapplication;

import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Iterator;

public class Owner {

	public static ArrayList<Book> books = new ArrayList<>();
	public static ArrayList<Customer> customers = new ArrayList<>();

	public Owner() {
	
		try {
			FileReader fbooks = new FileReader("books.txt"), fcustomers = new FileReader("customers.txt");
			Scanner sb = new Scanner(fbooks), sc = new Scanner(fcustomers);

			String temp;

			while (sb.hasNextLine()) {
				temp = sb.nextLine();
				String[] split = temp.split("\\s+");
				books.add(new Book(split[0], Integer.parseInt(split[1])));
			}

			while (sc.hasNextLine()) {
				temp = sc.nextLine();
				String[] split = temp.split("\\s+");
				customers.add(new Customer(split[0], split[1], Integer.parseInt(split[2])));
			}

			sb.close();
			sc.close();
		} catch (IOException e) {
			System.err.println("file reading error");
			e.printStackTrace();
		}
	}

    public void addBook(Book book) {
		books.add(book);
                System.out.println("Added: " + book.getTitle());
    }

    public void delBook(Book book) {
        for (int i = 0; i < books.size(); i++) {
            if (book.getTitle().compareTo(books.get(i).getTitle()) == 0){
                books.remove(i);
                return;
            }
        }
    }

    public void addCustomer(Customer customer) {
		customers.add(customer);
    }

    public void delCustomer(Customer customer) {       
        for (int i = 0; i < customers.size(); i++) {
            if (customer.getUserName().compareTo(customers.get(i).getUserName()) == 0){
                customers.remove(i);
                return;
            }
        }
    }

	protected void finalize() {
		Iterator<Book> itB = books.iterator();
		Iterator<Customer> itC = customers.iterator();

		try {
			FileWriter outB = new FileWriter("books.txt", false);
			FileWriter outC = new FileWriter("customers.txt", false);

			while (itB.hasNext()) {
				Book temp = itB.next();
				outB.append(temp.getTitle() + " " + temp.getPrice() + "\n");
			}

			while (itC.hasNext()) {
				Customer temp = itC.next();
				outC.append(temp.getUserName() + " " + temp.getPassword() + " " + temp.getPoints() + "\n");
			}

			outB.close();
			outC.close();
		} catch (IOException e) {
			System.err.println("file writing error");
			e.printStackTrace();
		}

	}
        
        public static void main(String[] args) {
            Owner o = new Owner();
            o.addBook(new Book("neato", 100));
            o.addBook(new Book("cat", 250));
            o.finalize();
        }
}