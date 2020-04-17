package net.devstudy.ishop.entity;

public class Account extends AbstractEntity<Integer> {
    private static final long serialVersionUID = 4056774543421789188L;
    private String name;
    private String email;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return String.format("Account [id=%s, name=%s, rmail=%s]",getId(),name,email);
    }
}
