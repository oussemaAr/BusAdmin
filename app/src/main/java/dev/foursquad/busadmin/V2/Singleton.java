package dev.foursquad.busadmin.V2;

class Singleton {
    private static final Singleton ourInstance = new Singleton();
    String id;

    static Singleton getInstance() {
        return ourInstance;
    }

    private Singleton() {
    }
}
