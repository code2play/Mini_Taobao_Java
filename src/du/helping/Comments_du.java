package du.helping;

/**
 * 评论类，封装了评论者，评论时间，以及评论内容
 * Created by ShuhanShen on 07／13／17.
 */
public class Comments_du {

    private String author;
    private String timeAndDate;
    private String comment;
    private int index;

    public Comments_du(){}
    public Comments_du(String author, String timeAndDate, String comment) {
        this.author = author;
        this.timeAndDate = timeAndDate;
        this.comment = comment;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTimeAndDate() {
        return timeAndDate;
    }

    public void setTimeAndDate(String timeAndDate) {
        this.timeAndDate = timeAndDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
}
