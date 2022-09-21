package my.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@IdClass(ReceiptPK.class)
public class Receipt {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "user_id", nullable = false)
    private Integer userId;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "book_id", nullable = false)
    private Integer bookId;
    @Basic
    @Column(name = "creation_time", nullable = false)
    private Timestamp creationTime;
    @Basic
    @Column(name = "receipt_status_id", nullable = false)
    private Integer receiptStatusId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public Timestamp getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Timestamp creationTime) {
        this.creationTime = creationTime;
    }

    public Integer getReceiptStatusId() {
        return receiptStatusId;
    }

    public void setReceiptStatusId(Integer receiptStatusId) {
        this.receiptStatusId = receiptStatusId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Receipt receipt = (Receipt) o;

        if (id != null ? !id.equals(receipt.id) : receipt.id != null) return false;
        if (userId != null ? !userId.equals(receipt.userId) : receipt.userId != null) return false;
        if (bookId != null ? !bookId.equals(receipt.bookId) : receipt.bookId != null) return false;
        if (creationTime != null ? !creationTime.equals(receipt.creationTime) : receipt.creationTime != null)
            return false;
        if (receiptStatusId != null ? !receiptStatusId.equals(receipt.receiptStatusId) : receipt.receiptStatusId != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (bookId != null ? bookId.hashCode() : 0);
        result = 31 * result + (creationTime != null ? creationTime.hashCode() : 0);
        result = 31 * result + (receiptStatusId != null ? receiptStatusId.hashCode() : 0);
        return result;
    }
}
