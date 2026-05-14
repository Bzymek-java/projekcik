package model;

public class FriendRequest {
    public enum Status { PENDING, ACCEPTED, REJECTED }

    private int id;
    private Status status = Status.PENDING;

    public FriendRequest() {}
    public FriendRequest(int id) { this.id = id; }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public Status getStatus() { return status; }

    public void accept() {
        this.status = Status.ACCEPTED;
    }

    public void reject() {
        this.status = Status.REJECTED;
    }
}

