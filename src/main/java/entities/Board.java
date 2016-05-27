package entities;

public class Board {
    private final int id;
    private final String address;
    private final String name;
    private final String description;

    public Board(int id, String address, String name, String description) {
        this.id = id;
        this.address = address;
        this.name = name;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Board board = (Board) o;

        if (id != board.id) return false;
        if (address != null ? !address.equals(board.address) : board.address != null) return false;
        if (name != null ? !name.equals(board.name) : board.name != null) return false;
        return description != null ? description.equals(board.description) : board.description == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Board{" +
                "id=" + id +
                ", address='" + address + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
