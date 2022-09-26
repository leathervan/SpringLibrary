package my.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class Receipt {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic
    @Column(name = "creation_time", nullable = false)
    private Timestamp creationTime;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User userByUserId;
    @ManyToOne
    @JoinColumn(name = "book_id", referencedColumnName = "id", nullable = false)
    private Book bookByBookId;
    @ManyToOne
    @JoinColumn(name = "receipt_status_id", referencedColumnName = "id", nullable = false)
    private ReceiptStatus receiptStatusByReceiptStatusId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Timestamp getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Timestamp creationTime) {
        this.creationTime = creationTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Receipt receipt = (Receipt) o;

        if (id != null ? !id.equals(receipt.id) : receipt.id != null) return false;
        if (creationTime != null ? !creationTime.equals(receipt.creationTime) : receipt.creationTime != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (creationTime != null ? creationTime.hashCode() : 0);
        return result;
    }

    public User getUserByUserId() {
        return userByUserId;
    }

    public void setUserByUserId(User userByUserId) {
        this.userByUserId = userByUserId;
    }

    public Book getBookByBookId() {
        return bookByBookId;
    }

    public void setBookByBookId(Book bookByBookId) {
        this.bookByBookId = bookByBookId;
    }

    public ReceiptStatus getReceiptStatusByReceiptStatusId() {
        return receiptStatusByReceiptStatusId;
    }

    public void setReceiptStatusByReceiptStatusId(ReceiptStatus receiptStatusByReceiptStatusId) {
        this.receiptStatusByReceiptStatusId = receiptStatusByReceiptStatusId;
    }

}
