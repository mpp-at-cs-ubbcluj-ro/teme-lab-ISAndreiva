package internal.andreiva;

public interface Identifiable<ID> {
    void setId(ID id);
    ID getId();
}
