package com.advanced.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Models a table the combines person with their stocks.
 */
@Entity
@Table(name = "userstocks", catalog = "stocks")
public class UserStocks {
    private int id;
    private Person person;
    private String stockSymbol;

    /**
     * Create a UserStock that needs to be initialized
     */
    public UserStocks() {
        // this empty constructor is required by hibernate framework

    }

    /**
     * Create a valid UserStock
     *
     * @param person the person to assign the stockSymbol to
     * @param stockSymbol  the stockSymbol to associate the person with
     */
    public UserStocks(Person person, String stockSymbol) {
    	setStockSymbol(stockSymbol);
        setPerson(person);
    }

    /**
     * Primary Key - Unique ID for a particular row in the userstocks table.
     *
     * @return an integer value
     */
    @Id
    @Column(name = "ID", nullable = false, insertable = true, updatable = true)
    public int getId() {
        return id;
    }

    /**
     * Set the unique ID for a particular row in the userstocks table.
     * This method should not be called by client code. The value is managed in internally.
     *
     * @param id a unique value.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @return get the Person associated with this stockSymbol
     */
    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "ID", nullable = false)
    public Person getPerson() {
        return person;
    }

    /**
     * Specify the Person associated with the stockSymbol.
     *
     * @param person a person instance
     */
    public void setPerson(Person person) {
        this.person = person;
    }

    /**
     *
     * @return get the stockSymbol  associated with this Person
     */
    @Basic
    @Column(name = "stock_symbol", nullable = false, insertable = true, updatable = true, length = 4)
    public String getStockSymbol() {
        return stockSymbol;
    }

    /**
     * Specify the stockSymbol associated with the Person.
     *
     * @param stockSymbol a person instance
     */
    public void setStockSymbol(String stockSymbol) {
        this.stockSymbol = stockSymbol;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserStocks that = (UserStocks) o;

        if (id != that.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + person.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "UserStocks{" +
                "id=" + id +
                ", person=" + person +
                ", stockSymbol=" + stockSymbol +
                '}';
    }
}
