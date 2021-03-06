import plugin.*;

module handleRequest {
    requires spi;
    requires utils;
    requires database;
    requires com.google.gson;
    provides spi.Spi with AddMovie, FindMovies, GetFile, Default, Index, LoadDB;
}