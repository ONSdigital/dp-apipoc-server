package uk.gov.ons.api.model;

public class Contact {
    private String email;
    private String name;
    private String telephone;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Contact contact = (Contact) o;

        if (!email.equals(contact.email)) return false;
        if (!name.equals(contact.name)) return false;
        return telephone.equals(contact.telephone);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = email.hashCode();
        result = prime * result + name.hashCode();
        result = prime * result + telephone.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", telephone='" + telephone + '\'' +
                '}';
    }
}
