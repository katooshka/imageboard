package entities;

import com.google.auto.value.AutoValue;

import static data.Constants.ID_NOT_SET;

@AutoValue
public abstract class Board {
    public abstract int getId();
    public abstract String getAddress();
    public abstract String getName();
    public abstract String getDescription();
    public abstract String getImagePath();

    public static Builder builder() {
        return new AutoValue_Board.Builder().setId(ID_NOT_SET).setImagePath("");
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder setId(int id);
        public abstract Builder setAddress(String address);
        public abstract Builder setName(String name);
        public abstract Builder setDescription(String description);
        public abstract Builder setImagePath(String imagePath);
        public abstract Board build();
    }

}
