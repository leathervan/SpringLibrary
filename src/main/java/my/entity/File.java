package my.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class File {
    @Id
    @Basic
    @Column(name = "book_id", nullable = false)
    private Integer bookId;
    @Basic
    @Column(name = "pic", nullable = true, length = 90)
    private String pic;
    @Basic
    @Column(name = "text", nullable = true, length = 90)
    private String text;
    @Basic
    @Column(name = "audio", nullable = true, length = 90)
    private String audio;

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        File file = (File) o;

        if (bookId != null ? !bookId.equals(file.bookId) : file.bookId != null) return false;
        if (pic != null ? !pic.equals(file.pic) : file.pic != null) return false;
        if (text != null ? !text.equals(file.text) : file.text != null) return false;
        if (audio != null ? !audio.equals(file.audio) : file.audio != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = bookId != null ? bookId.hashCode() : 0;
        result = 31 * result + (pic != null ? pic.hashCode() : 0);
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + (audio != null ? audio.hashCode() : 0);
        return result;
    }
}
