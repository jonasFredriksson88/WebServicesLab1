module core {
    requires spi;
    uses spi.Spi;
    requires utils;
    requires com.google.gson;
    requires database;
    opens consumer to com.google.gson;
}